package codersbay.vienna.nachhilfe.wien.backend.dto.userdto;

import codersbay.vienna.nachhilfe.wien.backend.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private long id;
    private UserType userType;
    private String firstName;
    private String lastName;

}
