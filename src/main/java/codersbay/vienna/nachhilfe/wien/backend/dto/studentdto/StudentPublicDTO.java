package codersbay.vienna.nachhilfe.wien.backend.dto.studentdto;

import codersbay.vienna.nachhilfe.wien.backend.model.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentPublicDTO {
    Long studentId;
    String firstName;
    String lastName;
    String image;
    String description;
    UserType userType = UserType.STUDENT;
}
