package codersbay.vienna.nachhilfe.wien.backend.model.updaterequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateRequest {
    @Size(min=3)
    private String firstName;
    @Size(min=3)
    private String lastName;
    @Size(min=3)
    private String description;

    private boolean active;
    @Size(min=8)
    private String password;
    @Email
    private String email;

}

