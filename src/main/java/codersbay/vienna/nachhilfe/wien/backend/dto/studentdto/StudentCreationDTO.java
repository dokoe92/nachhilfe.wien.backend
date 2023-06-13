package codersbay.vienna.nachhilfe.wien.backend.dto.studentdto;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.ProfileDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentCreationDTO {
    String firstName;
    String lastName;
    String image;
    LocalDate birthdate;
    ProfileDTO profile;
}
