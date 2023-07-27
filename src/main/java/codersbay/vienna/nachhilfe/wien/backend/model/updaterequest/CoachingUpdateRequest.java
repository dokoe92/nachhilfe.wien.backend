package codersbay.vienna.nachhilfe.wien.backend.model.updaterequest;

import codersbay.vienna.nachhilfe.wien.backend.model.Level;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachingUpdateRequest {

    Level level;
    Double rate;
    Boolean active;


}
