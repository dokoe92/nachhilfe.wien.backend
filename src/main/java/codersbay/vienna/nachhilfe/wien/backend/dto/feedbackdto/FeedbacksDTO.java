package codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class FeedbacksDTO {
    private Set<FeedbackDTO> feedbacks = new HashSet<>();
}
