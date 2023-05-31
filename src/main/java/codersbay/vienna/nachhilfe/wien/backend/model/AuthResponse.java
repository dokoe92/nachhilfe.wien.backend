package codersbay.vienna.nachhilfe.wien.backend.model;

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
