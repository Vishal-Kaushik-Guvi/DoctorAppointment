package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JFS6WDE.DoctorAppointmentApplication.Entity.Appointment;
import JFS6WDE.DoctorAppointmentApplication.Repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public void createAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointmentById(long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public Appointment viewAppointmentById(long id) {
        return appointmentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID: " + id));
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }

        Optional<Appointment> existingAppointmentOptional = appointmentRepository.findById(appointment.getAppointmentId());
        if (!existingAppointmentOptional.isPresent()) {
            throw new EntityNotFoundException("Appointment with ID " + appointment.getAppointmentId() + " not found");
        }

        Appointment existingAppointment = existingAppointmentOptional.get();
        existingAppointment.setAppointmentSlot(appointment.getAppointmentSlot());
        existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
        existingAppointment.setAppointmentTime(appointment.getAppointmentTime());
        existingAppointment.setAppointmentStatus(appointment.getAppointmentStatus());
        existingAppointment.setDoctor(appointment.getDoctor());
        existingAppointment.setPatient(appointment.getPatient());

        appointmentRepository.save(existingAppointment);
    }
}