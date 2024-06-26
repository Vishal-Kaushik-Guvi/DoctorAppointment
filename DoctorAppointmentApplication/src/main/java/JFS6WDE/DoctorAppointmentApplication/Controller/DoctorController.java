package JFS6WDE.DoctorAppointmentApplication.Controller;

import JFS6WDE.DoctorAppointmentApplication.Entity.Doctor;
import JFS6WDE.DoctorAppointmentApplication.Service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    
    @GetMapping("/doctorList")
    public String showDoctors(Model model) {
        return findPaginated(1, "name", "asc", model);
    }

    @GetMapping("/addDoctor")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "adddoctor";
    }

    @PostMapping("/addDoctor")
    public String addDoctor(@Valid @ModelAttribute("doctor") Doctor doctor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "adddoctor";
        }
        doctorService.createDoctor(doctor);
        return "redirect:/doctorList";
    }

    @GetMapping("/updateDoctor")
    public String showUpdateDoctorForm(@RequestParam("id") Long id, Model model) {
        Doctor doctor = doctorService.viewDoctorById(id);
        model.addAttribute("doctor", doctor);
        return "updatedoctor";
    }

    @PostMapping("/updateDoctor")
    public String updateDoctor(@Valid @ModelAttribute("doctor") Doctor doctor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "updatedoctor";
        }
        doctorService.updateDoctor(doctor);
        return "redirect:/doctorList";
    }

    @PostMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("id") Long id) {
        doctorService.deleteDoctorById(id);
        return "redirect:/doctorList";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        Page<Doctor> page = doctorService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Doctor> listDoctors = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("doctors", listDoctors);
        return "doctorlist";
    }
}
