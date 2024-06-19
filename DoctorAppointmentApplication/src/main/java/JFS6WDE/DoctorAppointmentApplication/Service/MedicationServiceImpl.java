package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JFS6WDE.DoctorAppointmentApplication.Entity.Medication;
import JFS6WDE.DoctorAppointmentApplication.Exception.ResourceNotFound;
import JFS6WDE.DoctorAppointmentApplication.Repository.MedicationRepository;

@Service
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    @Override
    public Medication createMedication(Medication medication) {
        return medicationRepository.save(medication);
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
    public void updateMedication(Medication medication) {
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
        existingMedication.setDoctor(medication.getDoctor());

        medicationRepository.save(existingMedication);
    }
}
