package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;

import JFS6WDE.DoctorAppointmentApplication.Entity.Medication;

public interface MedicationService {
List<Medication> getAllMedications();
Medication createMedication(Medication medic);
void deleteMedicationById(long id);
Medication viewMedicationById(long id);
void updateMedication(Medication medication);
}
