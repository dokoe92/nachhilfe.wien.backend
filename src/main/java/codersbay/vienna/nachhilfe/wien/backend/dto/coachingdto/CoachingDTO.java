package codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto;

import codersbay.vienna.nachhilfe.wien.backend.model.Level;
import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachingDTO implements Comparable<CoachingDTO>{

    private Long coachingId;
    private Long teacherId;
    @NotNull
    private Subject subject;
    private Level level;
    @NotNull
    @Positive
    private Double rate;
    private Boolean active;

    @Override
    public int compareTo(CoachingDTO coachingDTO) {
        return this.subject.name().compareToIgnoreCase(coachingDTO.subject.name());
    }
}
