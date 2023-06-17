package codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbacksDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.District;
import codersbay.vienna.nachhilfe.wien.backend.model.Feedback;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TeacherPublicDTO {
    Long teacherId;
    String firstName;
    String lastName;
    String description;
    String image;
    Boolean active;
    Double averageRatingScore;
    FeedbacksDTO feedbacks = new FeedbacksDTO();
    Set<CoachingDTO> coachings = new HashSet<>();
    Set<District> districts = new HashSet<>();

}
