package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.updaterequest.StudentUpdateRequest;
import codersbay.vienna.nachhilfe.wien.backend.model.updaterequest.TeacherUpdateRequest;
import codersbay.vienna.nachhilfe.wien.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> findAllStudents(){
        List<Student> studentList = studentService.findAllStudents();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }


    @PutMapping("/updateStudent/{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long studentId,
            @RequestBody StudentUpdateRequest request
    ) {
        Student updatedStudent =
                studentService.updateStudent (studentId,
                        request.getFirstName(),
                        request.getLastName(),
                        request.getDescription());
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }
}
