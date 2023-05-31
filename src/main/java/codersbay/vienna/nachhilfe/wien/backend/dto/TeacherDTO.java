package codersbay.vienna.nachhilfe.wien.backend.dto;

import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TeacherDTO {
    String firstName;
    String lastName;
    LocalDate birthdate;
    ProfileDTO profile;
    String description;
    Set<Coaching> coachings = new HashSet<>();
}
