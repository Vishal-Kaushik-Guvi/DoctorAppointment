package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;

import JFS6WDE.DoctorAppointmentApplication.Dto.AppointmentDto;
import JFS6WDE.DoctorAppointmentApplication.Entity.Appointment;

public interface AppointmentService {
	
List<Appointment> getAllAppointments();
void deleteAppointmentById(long appointmentId);
Appointment viewAppointmentById(long appointmentId);

void createAppointment(AppointmentDto appointmentdto, String name, long doctorId);
}
