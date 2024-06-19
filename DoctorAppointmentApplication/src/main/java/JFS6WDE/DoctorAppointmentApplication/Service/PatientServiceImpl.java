package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JFS6WDE.DoctorAppointmentApplication.Entity.Patient;
import JFS6WDE.DoctorAppointmentApplication.Exception.ResourceNotFound;
import JFS6WDE.DoctorAppointmentApplication.Repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient viewPatientById(long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (!patientOptional.isPresent()) {
            throw new ResourceNotFound("Patient with ID " + id + " not found");
        }
        return patientOptional.get();
    }

    @Override
    public void deletePatientById(long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFound("Patient with ID " + id + " not found");
        }
        patientRepository.deleteById(id);
    }

    @Override
    public void updatePatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }

        Optional<Patient> existingPatientOptional = patientRepository.findById(patient.getId());
        if (!existingPatientOptional.isPresent()) {
            throw new ResourceNotFound("Patient with ID " + patient.getId() + " not found");
        }

        Patient existingPatient = existingPatientOptional.get();
        existingPatient.setName(patient.getName());
        existingPatient.setMedicalHistory(patient.getMedicalHistory());
        existingPatient.setAppointments(patient.getAppointments());

        patientRepository.save(existingPatient);
    }
}
