package codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CoachingsDTO {

    @NotNull
    Set<CoachingDTO> coachings = new HashSet<>();
}
