package codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherPublicDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherPublicMapper {

    public TeacherPublicDTO toDTO(Teacher teacher) {
        TeacherPublicDTO teacherPublicDTO = new TeacherPublicDTO();
        teacherPublicDTO.setTeacherId(teacher.getId());
        teacherPublicDTO.setFirstName(teacher.getFirstName());
        teacherPublicDTO.setLastName(teacherPublicDTO.getLastName());
        teacherPublicDTO.setDescription(teacherPublicDTO.getDescription());
        teacherPublicDTO.setImage(teacherPublicDTO.getImage());
        teacherPublicDTO.setActive(teacher.getProfile().isActive());
        teacherPublicDTO.setAverageRatingScore(teacher.getProfile().getAverageRatingScore());
        teacherPublicDTO.setFeedbacks(teacher.getFeedbacks());
        teacherPublicDTO.setDistricts(teacher.getDisctricts());

        return teacherPublicDTO;
    }
}
