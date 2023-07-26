package codersbay.vienna.nachhilfe.wien.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    @Email
    String email;
    @Size(min=8)
    String password;
}
