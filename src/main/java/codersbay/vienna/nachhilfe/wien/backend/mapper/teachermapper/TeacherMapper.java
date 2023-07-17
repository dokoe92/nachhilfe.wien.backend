package codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherCreationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.ProfileMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherMapper {

    private final ProfileMapper profileMapper;

    public TeacherCreationDTO toDTO(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherCreationDTO teacherDTO = new TeacherCreationDTO();
        teacherDTO.setFirstName(teacher.getFirstName());
        teacherDTO.setLastName(teacher.getLastName());
        if (teacher.getProfile() != null) {
            teacherDTO.setProfile(profileMapper.toDTO(teacher.getProfile()));
        }

        return teacherDTO;
    }

    public Teacher toEntity(TeacherCreationDTO teacherDTO) {
        if (teacherDTO == null) {
            return null;
        }

        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setBirthdate(teacherDTO.getBirthdate());
        if (teacherDTO.getProfile() != null) {
            teacher.setProfile(profileMapper.toEntity(teacherDTO.getProfile()));
        }

        return teacher;
    }


}
