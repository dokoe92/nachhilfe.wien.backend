package codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.ProfileDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TeacherCreationDTO {
    @Size(min=3)
    String firstName;
    @Size(min=3)
    String lastName;
    String image;
    @PastOrPresent
    LocalDate birthdate;
    @Size(min=3)
    String description;
    @NotNull
    ProfileDTO profile;
}
