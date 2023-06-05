package codersbay.vienna.nachhilfe.wien.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoachingRequest {

    private Subject subject;
    private String level;
    private Double rate;

}
