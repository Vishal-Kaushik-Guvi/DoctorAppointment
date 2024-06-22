package JFS6WDE.DoctorAppointmentApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import JFS6WDE.DoctorAppointmentApplication.Entity.Patient;

public interface PatientRepository extends JpaRepository<Patient,Long>{
Patient findByName(String name);
}
