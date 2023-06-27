package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.service.AppointmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@Tag(name = "Appointment")
@SecurityRequirement(name="bearerAuth")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("sendAppointment/{conversationId}/{coachingId}")
    public ResponseEntity<AppointmentDTO> sendAppointment(@RequestBody AppointmentDTO appointmentDTO, @PathVariable Long conversationId, @PathVariable Long coachingId) {
        AppointmentDTO appointment =  appointmentService.sendAppointment(appointmentDTO, conversationId, coachingId);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @PostMapping("/get-appointments/{userId}")
    public ResponseEntity<Set<AppointmentDTO>> getAllAppointments(@PathVariable Long userId) {
        Set<AppointmentDTO> appointments = appointmentService.getAllAppointments(userId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}
