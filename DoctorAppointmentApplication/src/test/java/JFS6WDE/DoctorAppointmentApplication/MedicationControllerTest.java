package JFS6WDE.DoctorAppointmentApplication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import JFS6WDE.DoctorAppointmentApplication.Controller.MedicationController;
import JFS6WDE.DoctorAppointmentApplication.Dto.MedicationDto;
import JFS6WDE.DoctorAppointmentApplication.Entity.Medication;
import JFS6WDE.DoctorAppointmentApplication.Service.MedicationService;
import JFS6WDE.DoctorAppointmentApplication.Service.UserService;

public class MedicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MedicationService medicationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private MedicationController medicationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(medicationController).build();
    }

    @Test
    public void testGetAllMedications() throws Exception {
        mockMvc.perform(get("/medicationList"))
                .andExpect(status().isOk())
                .andExpect(view().name("medicationlist"))
                .andExpect(model().attributeExists("medications"));
    }

    @Test
    public void testShowUpdateMedicationForm() throws Exception {
        Medication medication = new Medication();
        medication.setId(1L);
        when(medicationService.viewMedicationById(1L)).thenReturn(medication);

        mockMvc.perform(get("/updateMedication").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("updatemedication"))
                .andExpect(model().attributeExists("medication"))
                .andExpect(model().attribute("medication", medication));
    }

    @Test
    public void testUpdateMedication() throws Exception {
        mockMvc.perform(post("/updateMedication")
                .param("id", "1")
                .param("name", "Medication Name")
                .param("description", "Medication Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/medicationList"));

        verify(medicationService).updateMedication(any(Medication.class));
    }

    @Test
    public void testShowAddAppointmentForm() throws Exception {
        mockMvc.perform(get("/addMedication")
                .param("doctorId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("addmedication"))
                .andExpect(model().attributeExists("doctorId"))
                .andExpect(model().attributeExists("medication"));
    }

    @Test
    public void testCreateMedication() throws Exception {
        mockMvc.perform(post("/addMedication")
                .param("doctorId", "1")
                .param("name", "Medication Name")
                .param("description", "Medication Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/medicationList"));

        verify(medicationService).createMedication(any(MedicationDto.class), any(String.class), anyLong());
    }

    @Test
    public void testDeleteMedication() throws Exception {
        mockMvc.perform(post("/deleteMedication")
                .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/medicationList"));

        verify(medicationService).deleteMedicationById(anyLong());
    }
}