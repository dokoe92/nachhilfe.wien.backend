package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import codersbay.vienna.nachhilfe.wien.backend.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final JwtService jwtService;

    @PostMapping
    @Operation(description = "Post a feedback to a teacher")
    public ResponseEntity<FeedbackDTO> postFeedback(@RequestHeader("Authorization") String authHeader, @RequestBody FeedbackDTO feedbackDTO, HttpServletRequest request) {
        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);

        Optional<Student> student = studentRepository.findById(feedbackDTO.getStudentId());
        Optional<Teacher> teacher = teacherRepository.findById(feedbackDTO.getTeacherId());

        if (student.isEmpty()) {
            throw new NoSuchElementException("Student not found");
        }
        if (!student.get().getId().equals(userId)) {
            throw new UserNotAuthorizedException("User not authorized!");
        }

        if (teacher.isEmpty()) {
            throw new NoSuchElementException("Teacher not found");
        }

        String email = jwtService.extractUsername(authHeader.substring(7));
        if (student.get().getUsername().equals(email)) {
            try {
                return new ResponseEntity<>(feedbackService.sendFeedback(feedbackDTO), HttpStatus.CREATED);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create feedback: " + e.getMessage());
            }
        }

        return null;
    }

}
