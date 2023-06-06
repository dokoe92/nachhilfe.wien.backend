package codersbay.vienna.nachhilfe.wien.backend.dto;

import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CoachingsDTO {

    Long userId;
    Set<CoachingDTO> coachings = new HashSet<>();
}
