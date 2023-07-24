package codersbay.vienna.nachhilfe.wien.backend.dto.auth;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Districts;
import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import codersbay.vienna.nachhilfe.wien.backend.model.UserType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
public class AuthResponse {

    Long userId;
    String token;
    UserType userType;
    String firstName;
    String lastName;
    LocalDate birthdate;
    String email;
    String description;
    String image;
    Boolean active;
    Double averageRatingScore;
    Set<FeedbackDTO> feedbacks = new HashSet<>();
    Set<CoachingDTO> coachings = new HashSet<>();
    Set<ConversationDTO> conversations = new LinkedHashSet<>();
    Set<Districts> districts = new HashSet<>();
    Set<Subject> availableSubjects = new HashSet<>();
}
