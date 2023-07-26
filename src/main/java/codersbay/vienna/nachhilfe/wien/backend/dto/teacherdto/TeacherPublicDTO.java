package codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Districts;
import codersbay.vienna.nachhilfe.wien.backend.model.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
    Set<FeedbackDTO> feedbacks = new TreeSet<>();
    Set<CoachingDTO> coachings = new TreeSet<>();
    Set<Districts> districts = new HashSet<>();
    UserType userType = UserType.TEACHER;

}
