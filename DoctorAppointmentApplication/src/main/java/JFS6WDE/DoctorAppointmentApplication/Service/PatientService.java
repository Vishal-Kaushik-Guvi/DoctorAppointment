package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;

import JFS6WDE.DoctorAppointmentApplication.Entity.Patient;

public interface PatientService {
List<Patient> getAllPatients();
Patient createPatient(Patient patient);
Patient viewPatientById(long id);
void deletePatientById(long id);
void updatePatient(Patient patient);
}
