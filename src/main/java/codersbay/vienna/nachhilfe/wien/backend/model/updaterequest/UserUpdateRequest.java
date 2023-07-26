package codersbay.vienna.nachhilfe.wien.backend.model.updaterequest;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateRequest {
    @Size(min=3)
    String firstName;
    @Size(min=3)
    String lastName;
    @PastOrPresent
    LocalDate birthdate;
    @Size(min=3)
    String description;
}
