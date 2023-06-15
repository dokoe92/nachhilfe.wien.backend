package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.service.CoachingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coaching")
@Tag(name = "Coaching")
@SecurityRequirement(name="bearerAuth")
public class CoachingController {

    private final CoachingMapper coachingMapper;
    private final CoachingService coachingService;
    private final TeacherRepository teacherRepository;

    /**
     * Creates coachings for a specific user.
     *
     * @param coachingsDTO  the CoachingsDTO object containing a set of coachings to be created
     * @param teacherId        the ID of the user
     * @return ResponseEntity with the created CoachingsDTO object and HTTP status CREATED
     */
    @PostMapping("/offer-coaching/{teacherId}")
    @Operation(
            description = "Add a coaching which will be offered by a teacher"
    )
    public ResponseEntity<CoachingsDTO> createCoachings(@RequestBody CoachingsDTO coachingsDTO, @PathVariable Long teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if (teacher.isEmpty()) {
            throw new NoSuchElementException("Teacher not found!");
        }

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (teacher.get().getUsername().equals(loggedInUsername)) {
            throw new AccessDeniedException("User is not allowed to do this");
        }

        CoachingsDTO savedCoachingsDTO = coachingService.createCoachings(coachingsDTO, teacherId);

        return new ResponseEntity<>(savedCoachingsDTO, HttpStatus.CREATED);
    }
}
