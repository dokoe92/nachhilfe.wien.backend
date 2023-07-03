package codersbay.vienna.nachhilfe.wien.backend.model.updaterequest;

import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachingUpdateRequest {

    String level;
    Double rate;
    Boolean active;


}
