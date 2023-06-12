package codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.ProfileMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherMapper {

    private final ProfileMapper profileMapper;

    public Teacher toEntity(TeacherDTO teacherDTO) {
        if (teacherDTO == null) {
            return null;
        }

        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setBirthdate(teacherDTO.getBirthdate());
        teacher.setDescription(teacherDTO.getDescription());
        teacher.setProfile(profileMapper.toEntity(teacherDTO.getProfile()));

        return teacher;
    }

    public TeacherDTO toDTO(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setFirstName(teacher.getFirstName());
        teacherDTO.setLastName(teacher.getLastName());
        teacherDTO.setProfile(profileMapper.toDTO(teacher.getProfile()));

        return teacherDTO;
    }
}
