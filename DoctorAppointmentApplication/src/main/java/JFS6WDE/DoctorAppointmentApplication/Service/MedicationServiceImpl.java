package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JFS6WDE.DoctorAppointmentApplication.Dto.MedicationDto;
import JFS6WDE.DoctorAppointmentApplication.Entity.Doctor;
import JFS6WDE.DoctorAppointmentApplication.Entity.Medication;
import JFS6WDE.DoctorAppointmentApplication.Entity.User;
import JFS6WDE.DoctorAppointmentApplication.Exception.ResourceNotFound;
import JFS6WDE.DoctorAppointmentApplication.Repository.DoctorRepository;
import JFS6WDE.DoctorAppointmentApplication.Repository.MedicationRepository;
import JFS6WDE.DoctorAppointmentApplication.Repository.UserRepository;

@Service
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    @Override
    public void deleteMedicationById(long id) {
        if (!medicationRepository.existsById(id)) {
            throw new ResourceNotFound("Medication with ID " + id + " not found");
        }
        medicationRepository.deleteById(id);
    }

    @Override
    public Medication viewMedicationById(long id) {
        Optional<Medication> medicationOptional = medicationRepository.findById(id);
        if (!medicationOptional.isPresent()) {
            throw new ResourceNotFound("Medication with ID " + id + " not found");
        }
        return medicationOptional.get();
    }

    @Override
    public Medication updateMedication(Medication medication) {
        if (medication == null) {
            throw new IllegalArgumentException("Medication cannot be null");
        }

        Optional<Medication> existingMedicationOptional = medicationRepository.findById(medication.getId());
        if (!existingMedicationOptional.isPresent()) {
            throw new ResourceNotFound("Medication with ID " + medication.getId() + " not found");
        }

        Medication existingMedication = existingMedicationOptional.get();
        existingMedication.setName(medication.getName());
        existingMedication.setDosage(medication.getDosage());
        existingMedication.setFrequency(medication.getFrequency());
        existingMedication.setStartDate(medication.getStartDate());
        existingMedication.setEndDate(medication.getEndDate());

        return medicationRepository.save(existingMedication);
    }

    @Override
    public Medication createMedication(MedicationDto medicationDto, String name, long id) {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new ResourceNotFound("User not found with username: " + name);
        }

        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFound("Doctor not found with ID: " + id));

        Medication medication = new Medication();

         medication.setName(medicationDto.getName());
         medication.setDosage(medicationDto.getDosage());
         medication.setFrequency(medicationDto.getFrequency());
         medication.setStartDate(medicationDto.getStartDate());
         medicationDto.setEndDate(medicationDto.getEndDate());

        medication.setDoctor(doctor);
        medication.setUser(user);

        return medicationRepository.save(medication);
    }
}
