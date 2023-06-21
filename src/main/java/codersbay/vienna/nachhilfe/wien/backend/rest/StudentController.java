package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.studentdto.StudentPublicDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.studentmapper.StudentPublicMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerAuth")
@Tag(name="Student")
public class StudentController {

    private final StudentService studentService;
    private final StudentPublicMapper studentPublicMapper;

    @GetMapping("/find-student/{studentId}")
    public ResponseEntity<StudentPublicDTO> findStudentById(@PathVariable Long studentId) {
        Student student = studentService.findStudentById(studentId);
        StudentPublicDTO studentPublicDTO = studentPublicMapper.toDTO(student);
        return new ResponseEntity<>(studentPublicDTO, HttpStatus.OK);
    }

}
