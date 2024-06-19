package JFS6WDE.DoctorAppointmentApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import JFS6WDE.DoctorAppointmentApplication.Entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

}
