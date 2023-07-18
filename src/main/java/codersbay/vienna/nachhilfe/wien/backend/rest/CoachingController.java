package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.updaterequest.CoachingUpdateRequest;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import codersbay.vienna.nachhilfe.wien.backend.service.CoachingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
    private static final Logger log = LoggerFactory.getLogger(CoachingController.class);

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
    public ResponseEntity<Set<CoachingDTO>> createCoachings(@RequestBody CoachingsDTO coachingsDTO,
                                                            @PathVariable Long teacherId,
                                                            HttpServletRequest request
                                                         ) {

        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);
        if (!userId.equals(teacherId)) {
            throw new UserNotAuthorizedException("User not authorized!");
        }
        Set<CoachingDTO> savedCoachingDTO = coachingService.createCoachings(coachingsDTO, teacherId);

        return new ResponseEntity<>(savedCoachingDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update-coaching/{coachingId}")
    public ResponseEntity<CoachingDTO> updateCoaching(@RequestBody CoachingUpdateRequest coachingUpdateRequest,
                                                      @PathVariable Long coachingId,
                                                      HttpServletRequest request
                                                      ) {
        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long teacherId = jwtService.extractUserId(token);
        CoachingDTO updatedCoaching = coachingService.updateCoaching(coachingUpdateRequest, coachingId, teacherId);
        return new ResponseEntity<>(updatedCoaching, HttpStatus.OK);

    }
}
