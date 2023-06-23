package codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherPublicDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
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
    private final CoachingMapper coachingMapper;

    public TeacherPublicDTO toDTO(Teacher teacher) {
        TeacherPublicDTO teacherPublicDTO = new TeacherPublicDTO();
        teacherPublicDTO.setTeacherId(teacher.getId());
        teacherPublicDTO.setFirstName(teacher.getFirstName());
        teacherPublicDTO.setLastName(teacher.getLastName());
        teacherPublicDTO.setDescription(teacher.getDescription());
        teacherPublicDTO.setImage(teacher.getProfile().getImageBase64());
        teacherPublicDTO.setActive(teacher.getProfile().isActive());
        teacherPublicDTO.setAverageRatingScore(teacher.getProfile().getAverageRatingScore());
        teacherPublicDTO.setFeedbacks(teacher.getFeedbacks()
                .stream()
                .map(feedbackMapper::toDTO)
                .collect(Collectors.toSet()));
        teacherPublicDTO.setDistricts(teacher.getDistricts());
        teacherPublicDTO.setCoachings(teacher.getCoachings()
                .stream().map(coachingMapper::toDTO)
                .collect(Collectors.toSet()));

        return teacherPublicDTO;
    }
}
