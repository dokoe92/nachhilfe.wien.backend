package codersbay.vienna.nachhilfe.wien.backend.dto.admindto;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.ProfileDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Districts;
import codersbay.vienna.nachhilfe.wien.backend.model.UserType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class UserDTO {
    Long userId;
    Long profileId;
    UserType userType;
    String firstName;
    String lastName;
    LocalDate birthdate;
    ProfileDTO profile;
    Boolean deleted;
    Set<FeedbackDTO> feedbacks = new TreeSet<>();
    Set<CoachingDTO> coachings = new TreeSet<>();
    Set<Districts> districts = new HashSet<>();
}
