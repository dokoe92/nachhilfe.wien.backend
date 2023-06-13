package codersbay.vienna.nachhilfe.wien.backend.dto.auth;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.District;
import codersbay.vienna.nachhilfe.wien.backend.model.Feedback;
import codersbay.vienna.nachhilfe.wien.backend.model.UserType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class AuthResponse {

    Long userId;
    String token;
    UserType userType;
    String email;
    String firstName;
    String lastName;
    LocalDate birthdate;
    String description;
    Set<Feedback> feedbacks = new HashSet<>();
    Set<CoachingDTO> coachings = new HashSet<>();
    Set<ConversationDTO> conversations = new HashSet<>();
    Set<District> districts = new HashSet<>();
}
