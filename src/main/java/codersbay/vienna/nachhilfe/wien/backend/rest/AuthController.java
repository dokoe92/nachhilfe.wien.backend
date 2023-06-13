package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.studentdto.StudentCreationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherCreationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.StudentMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherMapper;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthResponse;
import codersbay.vienna.nachhilfe.wien.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;

    /**
     * Creates a new student with a profile.
     *
     * @param studentDTO  the StudentDTO object containing the student and profile details
     * @return ResponseEntity with the created Student object and HTTP status CREATED
    */

    @PostMapping("/createStudent")
    public ResponseEntity<AuthResponse> createStudent(@RequestBody StudentCreationDTO studentDTO) {
        AuthResponse auth = authService.createStudentWithProfile(studentMapper.toEntity(studentDTO));
        return new ResponseEntity<>(auth, HttpStatus.CREATED);
    }

    /**
     * Creates a new teacher with a profile.
     *
     * @param teacherDTO  the TeacherDTO object containing the teacher and profile details
     * @return ResponseEntity with the created Teacher object and HTTP status CREATED
     */
    @PostMapping("/createTeacher")
    public ResponseEntity<AuthResponse> createTeacher(@RequestBody TeacherCreationDTO teacherDTO) {
        AuthResponse auth = authService.createTeacherWithProfile(teacherMapper.toEntity(teacherDTO));
        return new ResponseEntity<>(auth, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<String> createTestString(){
        return new ResponseEntity<>("Test", HttpStatus.OK);
    }
}
