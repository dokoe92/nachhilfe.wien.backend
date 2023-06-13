package codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto;

import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachingDTO {

    private Long coachingId;
    private Long userId;
    private Subject subject;
    private String level;
    private Double rate;
    private Boolean active;
}
