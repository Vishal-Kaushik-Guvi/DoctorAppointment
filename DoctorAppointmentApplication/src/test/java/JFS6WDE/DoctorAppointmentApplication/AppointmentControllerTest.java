package JFS6WDE.DoctorAppointmentApplication;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import JFS6WDE.DoctorAppointmentApplication.Controller.AppointmentController;
import JFS6WDE.DoctorAppointmentApplication.Dto.AppointmentDto;
import JFS6WDE.DoctorAppointmentApplication.Entity.Appointment;
import JFS6WDE.DoctorAppointmentApplication.Service.AppointmentService;
import JFS6WDE.DoctorAppointmentApplication.Service.UserService;

public class AppointmentControllerTest {

    @InjectMocks
    private AppointmentController appointmentController;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
    }

    @Test
    public void testViewAppointmentHistory() throws Exception {
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        when(appointmentService.getAllAppointments()).thenReturn(appointments);

        mockMvc.perform(get("/appointmentHistory"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointmenthistory"))
                .andExpect(model().attribute("appointments", appointments));

        verify(appointmentService).getAllAppointments();
    }

    @Test
    public void testDeleteAppointment() throws Exception {
        doNothing().when(appointmentService).deleteAppointmentById(anyLong());

        mockMvc.perform(post("/deleteAppointment/1"))
                .andExpect(status().isNotFound())
                .andExpect(redirectedUrl("/appointmentHistory"));

        verify(appointmentService).deleteAppointmentById(1L);
    }

    @Test
    public void testShowAddAppointmentForm() throws Exception {
        mockMvc.perform(get("/addAppointment").param("doctorId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("addappointment"))
                .andExpect(model().attributeExists("doctorId"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    public void testCreateAppointment_BindingErrors() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/addAppointment")
                        .param("doctorId", "1")
                        .flashAttr("appointment", new AppointmentDto()))
                .andExpect(status().isOk())
                .andExpect(view().name("addappointment"))
                .andExpect(model().attributeExists("appointment"))
                .andExpect(model().attribute("doctorId", 1L));
    }

}
