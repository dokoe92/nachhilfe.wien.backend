package codersbay.vienna.nachhilfe.wien.backend.dto.studentdto;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Districts;
import codersbay.vienna.nachhilfe.wien.backend.model.Feedback;
import codersbay.vienna.nachhilfe.wien.backend.model.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class StudentPublicDTO {
    Long studentId;
    String firstName;
    String lastName;
    String image;
    String description;
    UserType userType = UserType.STUDENT;
}
