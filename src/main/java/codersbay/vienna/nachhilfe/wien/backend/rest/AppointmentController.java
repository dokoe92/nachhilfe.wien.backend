package codersbay.vienna.nachhilfe.wien.backend.rest;


import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import codersbay.vienna.nachhilfe.wien.backend.service.AppointmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtService jwtService;

    @PostMapping("send-appointment/{conversationId}/{coachingId}")
    public ResponseEntity<AppointmentDTO> sendAppointment(@RequestBody AppointmentDTO appointmentDTO,
                                                          @PathVariable Long conversationId,
                                                          @PathVariable Long coachingId,
                                                          HttpServletRequest request) {
        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);
        AppointmentDTO appointment = appointmentService.sendAppointment(appointmentDTO, conversationId, coachingId, userId);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @PostMapping("/get-appointments/{userId}")
    public ResponseEntity<Set<AppointmentDTO>> getAllAppointments(@PathVariable Long userId) {
        Set<AppointmentDTO> appointments = appointmentService.getAllAppointments(userId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PutMapping("/confirm/{appointmentId}")
    public ResponseEntity<AppointmentDTO> confirmAppointment(@PathVariable Long appointmentId,
                                                             HttpServletRequest request) {

        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long teacherId = jwtService.extractUserId(token);
        if (!teacherId.equals(appointmentId)) {
            throw new UserNotAuthorizedException("Teacher not authorized!");
        }

        AppointmentDTO confirmedAppointment = appointmentService.confirmAppointment(appointmentId, teacherId);
        return new ResponseEntity<>(confirmedAppointment, HttpStatus.OK);
    }
}

////    @PutMapping("/reject/{appointmentId}/{teacherId}")
////    public ResponseEntity<AppointmentDTO> rejectAppointment(@PathVariable Long appointmentId,
////                                                            @PathVariable Long teacherId,
////                                                            HttpServletRequest request) {
////
////        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
////        Long userId = jwtService.extractUserId(token);
////        String role = jwtService.extractUserRole(token);  // You need to implement this method
////
////        if (!userId.equals(teacherId) && !role.equals("ROLE_TEACHER")) {
////            throw new UserNotAuthorizedException("User not authorized!");
////        }
////
////        AppointmentDTO rejectedAppointment = appointmentService.rejectAppointment(appointmentId);
////        return new ResponseEntity<>(rejectedAppointment, HttpStatus.OK);
////    }
//}
//
////    @PostMapping("/create/{studentId}/{teacherId}")
////    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO,
////                                                            @PathVariable Long studentId,
////                                                            @PathVariable Long teacherId,
////                                                            HttpServletRequest request) {
////
////        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
////        Long userId = jwtService.extractUserId(token);
////
////        AppointmentDTO savedAppointment = appointmentService.createAppointment(appointmentDTO, studentId , userId);
////        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
////    }
//
//    @PutMapping("/confirm/{appointmentId}/{teacherId}")
//    public ResponseEntity<AppointmentDTO> confirmAppointment(@PathVariable Long appointmentId,
//                                                             @PathVariable Long teacherId,
//                                                             HttpServletRequest request) {
//
//        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
//        Long userId = jwtService.extractUserId(token);
//        String role = jwtService.extractUserRole(token);  // You need to implement this method
//
//        if (!userId.equals(teacherId) && !role.equals("ROLE_TEACHER")) {
//            throw new UserNotAuthorizedException("User not authorized!");
//        }
//
//        AppointmentDTO confirmedAppointment = appointmentService.confirmAppointment(appointmentId);
//        return new ResponseEntity<>(confirmedAppointment, HttpStatus.OK);
//    }
//
//    @PutMapping("/reject/{appointmentId}/{teacherId}")
//    public ResponseEntity<AppointmentDTO> rejectAppointment(@PathVariable Long appointmentId,
//                                                            @PathVariable Long teacherId,
//                                                            HttpServletRequest request) {
//
//        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
//        Long userId = jwtService.extractUserId(token);
//        String role = jwtService.extractUserRole(token);  // You need to implement this method
//
//        if (!userId.equals(teacherId) && !role.equals("ROLE_TEACHER")) {
//            throw new UserNotAuthorizedException("User not authorized!");
//        }
//
//        AppointmentDTO rejectedAppointment = appointmentService.rejectAppointment(appointmentId);
//        return new ResponseEntity<>(rejectedAppointment, HttpStatus.OK);
//    }
//
//    @PutMapping("/reschedule/{appointmentId}/{teacherId}")
//    public ResponseEntity<AppointmentDTO> rescheduleAppointment(@PathVariable Long appointmentId,
//                                                                @PathVariable Long teacherId,
//                                                                @RequestBody AppointmentDTO appointmentDTO,
//                                                                HttpServletRequest request) {
//
//        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
//        Long userId = jwtService.extractUserId(token);
//        String role = jwtService.extractUserRole(token);  // You need to implement this method
//
//        if (!userId.equals(teacherId) && !role.equals("ROLE_TEACHER")) {
//            throw new UserNotAuthorizedException("User not authorized!");
//        }
//
//        AppointmentDTO rescheduledAppointment = appointmentService.rescheduleAppointment(appointmentId, appointmentDTO);
//        return new ResponseEntity<>(rescheduledAppointment, HttpStatus.OK);
//    }
