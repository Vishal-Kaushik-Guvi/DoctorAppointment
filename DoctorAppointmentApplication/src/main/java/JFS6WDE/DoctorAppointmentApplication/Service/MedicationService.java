package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;

import JFS6WDE.DoctorAppointmentApplication.Dto.MedicationDto;
import JFS6WDE.DoctorAppointmentApplication.Entity.Medication;

public interface MedicationService {
List<Medication> getAllMedications();
void deleteMedicationById(long id);
Medication viewMedicationById(long id);
Medication updateMedication(Medication medication);

Medication createMedication(MedicationDto medicationDto, String name, long id);
}
