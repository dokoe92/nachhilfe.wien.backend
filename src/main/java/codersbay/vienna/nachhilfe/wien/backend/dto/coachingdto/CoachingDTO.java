package codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto;

import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachingDTO {

    private Long coachingId;
    private Long teacherId;
    @NotNull
    private Subject subject;
    private String level;
    @NotNull
    @Positive
    private Double rate;
    private Boolean active;
}
