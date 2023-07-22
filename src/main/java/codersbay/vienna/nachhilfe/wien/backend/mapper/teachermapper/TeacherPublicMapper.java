package codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherPublicDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.feedbackmapper.FeedbackMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.feedbackmapper.FeedbacksMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeacherPublicMapper {


    private final FeedbackMapper feedbackMapper;
    private final CoachingMapper coachingMapper;
    private final TeacherRepository teacherRepository;

    public TeacherPublicDTO toDTO(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherPublicDTO teacherPublicDTO = new TeacherPublicDTO();
        teacherPublicDTO.setTeacherId(teacher.getId());
        teacherPublicDTO.setFirstName(teacher.getFirstName());
        teacherPublicDTO.setLastName(teacher.getLastName());
        if (teacher.getProfile() != null) {
            teacherPublicDTO.setDescription(teacher.getProfile().getDescription());
            teacherPublicDTO.setImage(teacher.getProfile().getImageBase64());
            teacherPublicDTO.setActive(teacher.getProfile().isActive());
            teacherPublicDTO.setAverageRatingScore(teacherRepository.findAverageRating(teacher.getId()));

            if (teacher.getFeedbacks() != null) {
                teacherPublicDTO.setFeedbacks(teacher.getFeedbacks()
                        .stream()
                        .map(feedbackMapper::toDTO)
                        .collect(Collectors.toSet()));
            }
        }

        teacherPublicDTO.setDistricts(teacher.getDistricts());

        if (teacher.getCoachings() != null) {
            teacherPublicDTO.setCoachings(teacher.getCoachings()
                    .stream().map(coachingMapper::toDTO)
                    .collect(Collectors.toSet()));
        }

        return teacherPublicDTO;
    }
}
