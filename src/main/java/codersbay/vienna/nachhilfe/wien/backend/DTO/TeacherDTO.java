package codersbay.vienna.nachhilfe.wien.backend.DTO;

import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TeacherDTO {
    String firstName;
    String lastName;
    LocalDate birthDate;
    ProfileDTO profile;
    String description;
    Set<Coaching> coachings = new HashSet<>();
}
