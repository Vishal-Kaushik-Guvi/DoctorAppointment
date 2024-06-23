package JFS6WDE.DoctorAppointmentApplication;

import static org.mockito.ArgumentMatchers.any;
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

import JFS6WDE.DoctorAppointmentApplication.Controller.DoctorController;
import JFS6WDE.DoctorAppointmentApplication.Entity.Doctor;
import JFS6WDE.DoctorAppointmentApplication.Service.DoctorService;

public class DoctorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    public void testShowAddDoctorForm() throws Exception {
        mockMvc.perform(get("/addDoctor"))
                .andExpect(status().isOk())
                .andExpect(view().name("adddoctor"))
                .andExpect(model().attributeExists("doctor"));
    }

    @Test
    public void testAddDoctor() throws Exception {
        mockMvc.perform(post("/addDoctor")
                .param("name", "Dr. John Doe")
                .param("specialty", "Cardiology")
                .param("email", "johndoe@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctorList"));
        
        verify(doctorService).createDoctor(any(Doctor.class));
    }

    @Test
    public void testShowUpdateDoctorForm() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(1L);
        when(doctorService.viewDoctorById(1L)).thenReturn(doctor);

        mockMvc.perform(get("/updateDoctor").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("updatedoctor"))
                .andExpect(model().attributeExists("doctor"))
                .andExpect(model().attribute("doctor", doctor));
    }

    @Test
    public void testUpdateDoctor() throws Exception {
        mockMvc.perform(post("/updateDoctor")
                .param("id", "1")
                .param("name", "Dr. John Doe")
                .param("specialty", "Cardiology")
                .param("email", "johndoe@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctorList"));

        verify(doctorService).updateDoctor(any(Doctor.class));
    }

    @Test
    public void testDeleteDoctor() throws Exception {
        mockMvc.perform(post("/deleteDoctor")
                .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctorList"));

        verify(doctorService).deleteDoctorById(1L);
    }
}