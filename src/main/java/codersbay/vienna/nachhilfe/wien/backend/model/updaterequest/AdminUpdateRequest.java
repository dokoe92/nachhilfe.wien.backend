package codersbay.vienna.nachhilfe.wien.backend.model.updaterequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminUpdateRequest {
    private String firstName;
    private String lastName;
    private String description;
    private boolean active;
    private String password;
    private String email;

}

