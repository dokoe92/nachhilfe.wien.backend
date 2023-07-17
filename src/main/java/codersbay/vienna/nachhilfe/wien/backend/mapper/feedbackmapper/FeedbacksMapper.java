package codersbay.vienna.nachhilfe.wien.backend.mapper.feedbackmapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbacksDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FeedbacksMapper {

    private final FeedbackMapper feedbackMapper;

    public FeedbacksDTO toDTO(Set<Feedback> feedbacks){
        FeedbacksDTO feedbacksDTO = new FeedbacksDTO();
        HashSet<FeedbackDTO> feedbackSet = new HashSet<>();
        for (Feedback feedback : feedbacks) {
            if (feedback != null) {
                feedbackSet.add(feedbackMapper.toDTO(feedback));
            }
        }
        feedbacksDTO.setFeedbacks(feedbackSet);
        return feedbacksDTO;
    }
}
