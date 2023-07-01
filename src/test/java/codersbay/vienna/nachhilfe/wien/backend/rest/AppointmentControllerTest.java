package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Appointment;
import codersbay.vienna.nachhilfe.wien.backend.model.Status;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.servlet.MockMvc;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.nio.cs.Surrogate.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private JwtService jwtService;

    @Test
        public void testConfirmAppointment() throws Exception {
            // Mock JWT token and teacher ID
            String token = "mockToken";
            Long teacherId = 1L;
            Long appointmentId = 2L;

            Teacher teacher = new Teacher();


            // Mock appointment and appointmentDTO
            Appointment appointment = new Appointment();
            appointment.getSender(teacherId);
            appointment.setStatus(Status.SCHEDULED);
            appointment.setConfirmed(false);

            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setId(1L);
            appointmentDTO.setSenderId(teacherId);
            appointmentDTO.setStatus(Status.CONFIRMED);
            appointmentDTO.setConfirmed(true);

            // Mock service behavior
            when(jwtService.getTokenFromHeader(anyString())).thenReturn(token);
            when(jwtService.extractUserId(anyString())).thenReturn(teacherId);
            when(appointmentService.confirmAppointment(1L, teacherId)).thenReturn(appointmentDTO);

            // Perform the request and assert the response
            mockMvc.perform(put("/appointments/confirm/1")
                            .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.teacherId", is(1)))
                    .andExpect(jsonPath("$.status", is("CONFIRMED")))
                    .andExpect(jsonPath("$.confirmed", is(true)));

            // Verify that the service method was called with the correct arguments
            verify(appointmentService, times(1)).confirmAppointment(1L, teacherId);
        }

    private AppointmentService verify(AppointmentService appointmentService, ExpectedCount times) {
    }
}
}