package JFS6WDE.DoctorAppointmentApplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import JFS6WDE.DoctorAppointmentApplication.Dto.AppointmentDto;
import JFS6WDE.DoctorAppointmentApplication.Entity.Appointment;
import JFS6WDE.DoctorAppointmentApplication.Entity.User;
import JFS6WDE.DoctorAppointmentApplication.Service.AppointmentService;
import JFS6WDE.DoctorAppointmentApplication.Service.UserService;
import jakarta.validation.Valid;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @GetMapping("/appointmentHistory")
    public String viewAppointmentHistory(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointmenthistory";
    }

    @PostMapping("/deleteAppointment/{appointmentId}")
    public String deleteAppointment(@PathVariable long appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);
        return "redirect:/appointmentHistory";
    }

    @GetMapping("/addAppointment")
    public String showAddAppointmentForm(@RequestParam long doctorId, Model model) {
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("appointment", new AppointmentDto());
        return "addappointment";
    }

    @PostMapping("/addAppointment")
    public String createAppointment(@RequestParam long doctorId, @Valid @ModelAttribute("appointment") AppointmentDto appointmentdto,
      BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("appointment", appointmentdto);
            model.addAttribute("doctorId", doctorId);
            return "addappointment";        
        }

                // Get the authenticated user's email from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // assuming email is the principal name

        // Fetch the user entity using the email
        User user = userService.findUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Extract the username (name) from the user entity
        String name = user.getName();
        
        appointmentService.createAppointment(appointmentdto, name, doctorId);
        return "redirect:/appointmentHistory"; // Redirect to a suitable view after saving
    }
}
