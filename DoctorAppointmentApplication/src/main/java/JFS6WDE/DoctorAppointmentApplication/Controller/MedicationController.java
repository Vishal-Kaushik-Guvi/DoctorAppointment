package JFS6WDE.DoctorAppointmentApplication.Controller;

import JFS6WDE.DoctorAppointmentApplication.Dto.MedicationDto;
import JFS6WDE.DoctorAppointmentApplication.Entity.Medication;
import JFS6WDE.DoctorAppointmentApplication.Entity.User;
import JFS6WDE.DoctorAppointmentApplication.Service.MedicationService;
import JFS6WDE.DoctorAppointmentApplication.Service.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private UserService userService;

    @GetMapping("/medicationList")
    public String getAllMedications(Model model) {
        List<Medication> medications = medicationService.getAllMedications();
        model.addAttribute("medications", medications);
        return "medicationlist";
    }

    @GetMapping("/updateMedication")
    public String showUpdateMedicationForm(@RequestParam("id") Long id, Model model) {
        Medication medication = medicationService.viewMedicationById(id);
        model.addAttribute("medication", medication);
        return "updatemedication";
    }

    @PostMapping("/updateMedication")
    public String updateMedication(@ModelAttribute("medication") Medication medication, Model model) {
        medicationService.updateMedication(medication);
        model.addAttribute("success", true);
        return "redirect:/medicationList";
    }

    @GetMapping("/addMedication")
    public String showAddAppointmentForm(@RequestParam long doctorId, Model model) {
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("medication", new MedicationDto());
        return "addmedication";
    }

    @PostMapping("/addMedication")
    public String createMedication(@RequestParam long doctorId, @Valid @ModelAttribute("medication") MedicationDto medicationDto,
      BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("medicationDto", medicationDto);
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
        
        medicationService.createMedication(medicationDto, name, doctorId);
        return "redirect:/medicationList"; // Redirect to a suitable view after saving
    }

    @PostMapping("/deleteMedication")
    public String deleteDoctor(@RequestParam("id") Long id) {
        medicationService.deleteMedicationById(id);
        return "redirect:/medicationList";
    }

}
