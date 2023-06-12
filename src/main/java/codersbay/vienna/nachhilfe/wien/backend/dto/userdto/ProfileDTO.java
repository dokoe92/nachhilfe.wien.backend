package codersbay.vienna.nachhilfe.wien.backend.dto.userdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    String username;
    String password;
    String email;
    String description;
    Boolean active;
}

