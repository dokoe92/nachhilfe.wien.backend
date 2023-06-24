package codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.ProfileDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TeacherCreationDTO {
    String firstName;
    String lastName;
    String image;
    LocalDate birthdate;
    String description;
    ProfileDTO profile;
}
