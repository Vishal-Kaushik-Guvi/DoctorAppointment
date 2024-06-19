package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;

import JFS6WDE.DoctorAppointmentApplication.Entity.Appointment;

public interface AppointmentService {
List<Appointment> getAllAppointments();
void createAppointment(Appointment appointment);
void deleteAppointmentById(long id);
Appointment viewAppointmentById(long id);
void updateAppointment(Appointment appointment);
}
