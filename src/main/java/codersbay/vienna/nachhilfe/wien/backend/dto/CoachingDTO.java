package codersbay.vienna.nachhilfe.wien.backend.dto;

import codersbay.vienna.nachhilfe.wien.backend.model.District;
import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CoachingDTO {

    private Long id;
    private Subject subject;
    private String level;
    private Double rate;
    private Boolean active;
    private Long userId;

}
