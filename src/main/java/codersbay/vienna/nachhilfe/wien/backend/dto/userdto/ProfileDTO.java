package codersbay.vienna.nachhilfe.wien.backend.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    @Size(min = 8)
    String password;
    @Email
    String email;
    String description;
    String imageBase64;
    Boolean active;
    Double averageRatingScore;
}

