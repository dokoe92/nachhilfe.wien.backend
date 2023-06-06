package codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto;

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
