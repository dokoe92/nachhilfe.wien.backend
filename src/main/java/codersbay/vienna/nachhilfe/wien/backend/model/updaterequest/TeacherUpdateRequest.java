package codersbay.vienna.nachhilfe.wien.backend.model.updaterequest;

import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class TeacherUpdateRequest {
    private String firstName;
    private String lastName;
    private String description;
    private boolean active;
    private String password;
    private String email;
    private Set<Coaching> coachings;
}
