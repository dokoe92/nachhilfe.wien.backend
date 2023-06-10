package codersbay.vienna.nachhilfe.wien.backend.model.updaterequest;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Status;
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
    private Set<Coaching> coachings;
}
