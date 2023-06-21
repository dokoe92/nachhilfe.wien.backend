package codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherPublicDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.feedbackmapper.FeedbackMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.feedbackmapper.FeedbacksMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeacherPublicMapper {

    private final FeedbacksMapper feedbacksMapper;
    private final FeedbackMapper feedbackMapper;

    public TeacherPublicDTO toDTO(Teacher teacher) {
        TeacherPublicDTO teacherPublicDTO = new TeacherPublicDTO();
        teacherPublicDTO.setTeacherId(teacher.getId());
        teacherPublicDTO.setFirstName(teacher.getFirstName());
        teacherPublicDTO.setLastName(teacherPublicDTO.getLastName());
        teacherPublicDTO.setDescription(teacherPublicDTO.getDescription());
        teacherPublicDTO.setImage(teacherPublicDTO.getImage());
        teacherPublicDTO.setActive(teacher.getProfile().isActive());
        teacherPublicDTO.setAverageRatingScore(teacher.getProfile().getAverageRatingScore());
        teacherPublicDTO.setFeedbacks(teacher.getFeedbacks()
                .stream()
                .map(feedbackMapper::toDTO)
                .collect(Collectors.toSet()));
        teacherPublicDTO.setDistricts(teacher.getDistricts());

        return teacherPublicDTO;
    }
}
