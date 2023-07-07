package codersbay.vienna.nachhilfe.wien.backend.model.updaterequest;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateRequest {
    String firstName;
    String lastName;
    LocalDate birthdate;
    String description;
}
