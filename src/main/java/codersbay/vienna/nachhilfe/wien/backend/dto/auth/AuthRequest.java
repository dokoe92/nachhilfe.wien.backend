package codersbay.vienna.nachhilfe.wien.backend.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    String email;
    String password;
}
