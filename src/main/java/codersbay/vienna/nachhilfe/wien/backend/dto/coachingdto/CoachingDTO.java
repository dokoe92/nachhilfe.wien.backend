package codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Subject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachingDTO {

    private Long coachingId;
    private Subject subject;
    private String level;
    private Double rate;
    private Boolean active;
    private Long teacherId;

}
