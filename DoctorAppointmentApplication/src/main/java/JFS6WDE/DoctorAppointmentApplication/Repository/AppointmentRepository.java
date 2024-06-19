package JFS6WDE.DoctorAppointmentApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import JFS6WDE.DoctorAppointmentApplication.Entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

}
