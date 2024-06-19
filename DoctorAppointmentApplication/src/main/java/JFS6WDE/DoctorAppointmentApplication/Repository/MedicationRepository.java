package JFS6WDE.DoctorAppointmentApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import JFS6WDE.DoctorAppointmentApplication.Entity.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
