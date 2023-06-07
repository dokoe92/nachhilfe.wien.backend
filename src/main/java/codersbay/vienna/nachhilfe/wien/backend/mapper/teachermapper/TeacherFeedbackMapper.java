package codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherFeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherFeedbackMapper {

    public TeacherFeedbackDTO toDTO(Teacher teacher) {
        TeacherFeedbackDTO dto = new TeacherFeedbackDTO();
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setUserName(teacher.getProfile().getUserName());
        dto.setAvgRating(teacher.getProfile().getAverageRatingScore());
        dto.setFeedbacks(teacher.getFeedback());
        return dto;
    }

}
