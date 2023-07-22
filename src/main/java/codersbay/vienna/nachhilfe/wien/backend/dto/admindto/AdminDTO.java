package codersbay.vienna.nachhilfe.wien.backend.dto.admindto;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.ProfileDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminDTO {
    @Size(min=3)
    String firstName;
    @Size(min=3)
    String lastName;
    @PastOrPresent
    LocalDate birthdate;
    @NotNull
    ProfileDTO profile;
}
