package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JFS6WDE.DoctorAppointmentApplication.Dto.AppointmentDto;
import JFS6WDE.DoctorAppointmentApplication.Entity.Appointment;
import JFS6WDE.DoctorAppointmentApplication.Entity.Doctor;
import JFS6WDE.DoctorAppointmentApplication.Entity.User;
import JFS6WDE.DoctorAppointmentApplication.Exception.ResourceNotFound;
import JFS6WDE.DoctorAppointmentApplication.Repository.AppointmentRepository;
import JFS6WDE.DoctorAppointmentApplication.Repository.DoctorRepository;
import JFS6WDE.DoctorAppointmentApplication.Repository.UserRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteAppointmentById(long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public Appointment viewAppointmentById(long appointmentId) {
        return appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID: " + appointmentId));
    }

    @Override
    public void createAppointment(AppointmentDto appointmentDto, String name, long doctorId) {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new ResourceNotFound("User not found with username: " + name);
        }

        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFound("Doctor not found with ID: " + doctorId));

        Appointment appointment = new Appointment();
        appointment.setAppointmentSlot(appointmentDto.getAppointmentSlot());
        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        appointment.setAppointmentTime(appointmentDto.getAppointmentTime());
        appointment.setAppointmentCharges(appointmentDto.getAppointmentSlot() * 1000);

        appointment.setDoctor(doctor);
        appointment.setUser(user);

        appointmentRepository.save(appointment);
    }
}