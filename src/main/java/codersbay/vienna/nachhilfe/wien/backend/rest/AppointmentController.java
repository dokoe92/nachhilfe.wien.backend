package codersbay.vienna.nachhilfe.wien.backend.rest;


import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Appointment;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import codersbay.vienna.nachhilfe.wien.backend.service.AppointmentService;
import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
@Tag(name = "Appointments")
@SecurityRequirement(name = "bearerAuth")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final JwtService jwtService;

    @PostMapping("/create/{studentId}/{teacherId}")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO,
                                                            @PathVariable Long studentId,
                                                            @PathVariable Long teacherId,
                                                            HttpServletRequest request) {

        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);

        AppointmentDTO savedAppointment = appointmentService.createAppointment(appointmentDTO, studentId , userId);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/confirm/{appointmentId}/{teacherId}")
    public ResponseEntity<AppointmentDTO> confirmAppointment(@PathVariable Long appointmentId,
                                                             @PathVariable Long teacherId,
                                                             HttpServletRequest request) {

        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);
        String role = jwtService.extractUserRole(token);  // You need to implement this method

        if (!userId.equals(teacherId) && !role.equals("ROLE_TEACHER")) {
            throw new UserNotAuthorizedException("User not authorized!");
        }

        AppointmentDTO confirmedAppointment = appointmentService.confirmAppointment(appointmentId);
        return new ResponseEntity<>(confirmedAppointment, HttpStatus.OK);
    }

    @PutMapping("/reject/{appointmentId}/{teacherId}")
    public ResponseEntity<AppointmentDTO> rejectAppointment(@PathVariable Long appointmentId,
                                                            @PathVariable Long teacherId,
                                                            HttpServletRequest request) {

        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);
        String role = jwtService.extractUserRole(token);  // You need to implement this method

        if (!userId.equals(teacherId) && !role.equals("ROLE_TEACHER")) {
            throw new UserNotAuthorizedException("User not authorized!");
        }

        AppointmentDTO rejectedAppointment = appointmentService.rejectAppointment(appointmentId);
        return new ResponseEntity<>(rejectedAppointment, HttpStatus.OK);
    }

    @PutMapping("/reschedule/{appointmentId}/{teacherId}")
    public ResponseEntity<AppointmentDTO> rescheduleAppointment(@PathVariable Long appointmentId,
                                                                @PathVariable Long teacherId,
                                                                @RequestBody AppointmentDTO appointmentDTO,
                                                                HttpServletRequest request) {

        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);
        String role = jwtService.extractUserRole(token);  // You need to implement this method

        if (!userId.equals(teacherId) && !role.equals("ROLE_TEACHER")) {
            throw new UserNotAuthorizedException("User not authorized!");
        }

        AppointmentDTO rescheduledAppointment = appointmentService.rescheduleAppointment(appointmentId, appointmentDTO);
        return new ResponseEntity<>(rescheduledAppointment, HttpStatus.OK);
    }
}