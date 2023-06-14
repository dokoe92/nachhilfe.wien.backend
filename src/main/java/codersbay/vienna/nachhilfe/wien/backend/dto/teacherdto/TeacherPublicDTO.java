package codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.District;
import codersbay.vienna.nachhilfe.wien.backend.model.Feedback;
import codersbay.vienna.nachhilfe.wien.backend.model.UserType;
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
    Set<Feedback> feedbacks = new HashSet<>();
    Set<CoachingDTO> coachings = new HashSet<>();
    Set<District> districts = new HashSet<>();

}
