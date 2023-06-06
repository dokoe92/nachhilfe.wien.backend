package codersbay.vienna.nachhilfe.wien.backend.mapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.StudentDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentMapper {

    private final ProfileMapper profileMapper;

    public Student toEntity(StudentDTO studentDTO) {
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

    public StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setBirthdate(student.getBirthdate());
        studentDTO.setProfile(profileMapper.toDTO(student.getProfile()));

        return studentDTO;
    }

}
