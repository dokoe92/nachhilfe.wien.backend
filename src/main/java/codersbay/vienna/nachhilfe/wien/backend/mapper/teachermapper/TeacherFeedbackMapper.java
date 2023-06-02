package codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherFeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.ProfileMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherFeedbackMapper {

    private final ProfileMapper profileMapper;

    public TeacherFeedbackDTO toDTO(Teacher teacher) {
        TeacherFeedbackDTO dto = new TeacherFeedbackDTO();
        dto.setActive(teacher.getProfile().isActive());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setUserName(teacher.getProfile().getUserName());
        dto.setDescription(teacher.getDescription());
        dto.setAvgRating(teacher.getProfile().getAverageRatingScore());
        dto.setFeedbacks(teacher.getFeedback());
        return dto;
    }

}
