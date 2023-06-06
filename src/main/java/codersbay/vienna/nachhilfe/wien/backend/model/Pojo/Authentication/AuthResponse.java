package codersbay.vienna.nachhilfe.wien.backend.model.Pojo.Authentication;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.UserType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponse {

    Long id;
    String accessToken;
    Boolean newMessage;
    UserType type;
    String email;
    String firstName;
    String lastName;
}
