package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.service.CoachingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coaching")
public class CoachingController {

    private final CoachingMapper coachingMapper;
    private final CoachingService coachingService;
    private final TeacherRepository teacherRepository;

    /**
     * Creates coachings for a specific user.
     *
     * @param coachingsDTO  the CoachingsDTO object containing a set of coachings to be created
     * @param userId        the ID of the user
     * @return ResponseEntity with the created CoachingsDTO object and HTTP status CREATED
     */
    @PostMapping("/{userId}")
    public ResponseEntity<CoachingsDTO> createCoachings(@RequestBody CoachingsDTO coachingsDTO, @PathVariable Long userId) {
        Optional<Teacher> teacher = teacherRepository.findById(userId);
        if (teacher.isEmpty()) {
            throw new NoSuchElementException("Teacher not found!");
        }

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (teacher.get().getUsername().equals(loggedInUsername)) {
            throw new AccessDeniedException("User is not allowed to do this");
        }

        CoachingsDTO savedCoachingsDTO = coachingService.createCoachings(coachingsDTO, userId);

        return new ResponseEntity<>(savedCoachingsDTO, HttpStatus.CREATED);
    }
}
