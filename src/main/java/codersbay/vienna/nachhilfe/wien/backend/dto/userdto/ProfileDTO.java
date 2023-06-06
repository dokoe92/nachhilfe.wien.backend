package codersbay.vienna.nachhilfe.wien.backend.dto.userdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    String userName;
    String password;
    String email;
    String description;
    Boolean active;
    Double averageRatingScore;

}

