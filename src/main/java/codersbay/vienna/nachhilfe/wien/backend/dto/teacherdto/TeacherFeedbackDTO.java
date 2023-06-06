package codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto;


import codersbay.vienna.nachhilfe.wien.backend.model.Feedback;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class TeacherFeedbackDTO {

    Long teacherId;
    String firstName;
    String lastName;
    String userName;
    String description;
    Double avgRating;
    Set<Feedback> feedbacks = new HashSet<>();

}
