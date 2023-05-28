package codersbay.vienna.nachhilfe.wien.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentDTO {

    String firstName;
    String lastName;
    LocalDate birthDate;
    ProfileDTO profile;
}