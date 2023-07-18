package codersbay.vienna.nachhilfe.wien.backend.mapper.studentmapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.studentdto.StudentPublicDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class StudentPublicMapper {

    public StudentPublicDTO toDTO(Student student) {
        StudentPublicDTO studentPublicDTO = new StudentPublicDTO();
        studentPublicDTO.setStudentId(student.getId());
        studentPublicDTO.setFirstName(student.getFirstName());
        studentPublicDTO.setLastName(student.getLastName());
        studentPublicDTO.setImage(student.getProfile().getImageBase64());
        studentPublicDTO.setDescription(studentPublicDTO.getDescription());

        return studentPublicDTO;
    }
}
