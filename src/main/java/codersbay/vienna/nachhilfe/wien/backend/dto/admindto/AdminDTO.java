package codersbay.vienna.nachhilfe.wien.backend.dto.admindto;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.ProfileDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminDTO {
    String firstName;
    String lastName;
    LocalDate birthdate;
    ProfileDTO profile;
}
