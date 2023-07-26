package codersbay.vienna.nachhilfe.wien.backend.searchobjects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearch {
    @Positive
    Long id;
    @Email
    String email;
}
