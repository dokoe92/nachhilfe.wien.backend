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
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
public class FindUserDTO {
    Long userId;
    Long profileId;
    UserType userType;
    String firstName;
    String lastName;
    LocalDate birthdate;
    ProfileDTO profile;
    Set<FeedbackDTO> feedbacks = new HashSet<>();
    Set<CoachingDTO> coachings = new HashSet<>();
    Set<Districts> districts = new HashSet<>();
}
