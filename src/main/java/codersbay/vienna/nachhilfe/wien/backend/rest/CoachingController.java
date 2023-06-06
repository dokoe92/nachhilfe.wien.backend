package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.service.CoachingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coaching")
public class CoachingController {

    private final CoachingService coachingService;

    @PostMapping("/{userId}")
    public ResponseEntity<CoachingsDTO> createCoachings(@RequestBody CoachingsDTO coachingsDTO, @PathVariable Long userId) {
        CoachingsDTO coachingDTO = coachingService.createCoachings(coachingsDTO,userId);
        return new ResponseEntity<>(coachingDTO, HttpStatus.CREATED);
    }
}