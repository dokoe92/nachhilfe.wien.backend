package codersbay.vienna.nachhilfe.wien.backend.mapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.studentdto.StudentCreationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.ProfileMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentMapper {

    private final ProfileMapper profileMapper;

    public Student toEntity(StudentCreationDTO studentDTO) {
        if (studentDTO == null) {
            return null;
        }
        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setBirthdate(studentDTO.getBirthdate());
        student.setProfile(profileMapper.toEntity(studentDTO.getProfile()));

        return student;
    }

    public StudentCreationDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentCreationDTO studentDTO = new StudentCreationDTO();
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setBirthdate(student.getBirthdate());
        studentDTO.setProfile(profileMapper.toDTO(student.getProfile()));

        return studentDTO;
    }

}
