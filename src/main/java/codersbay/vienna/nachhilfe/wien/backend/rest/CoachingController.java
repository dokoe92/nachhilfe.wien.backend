package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.service.CoachingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Objects;
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
    private final JwtService jwtService;

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
    public ResponseEntity<CoachingsDTO> createCoachings(@RequestBody CoachingsDTO coachingsDTO,
                                                        @PathVariable Long teacherId
                                                         ) {

        CoachingsDTO savedCoachingsDTO = coachingService.createCoachings(coachingsDTO, teacherId);
        return new ResponseEntity<>(savedCoachingsDTO, HttpStatus.CREATED);
    }
}
