package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.admindto.AdminDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.studentdto.StudentDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.StudentMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.adminmapper.AdminMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Admin;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.service.CoachingService;
import codersbay.vienna.nachhilfe.wien.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;
    private final AdminMapper adminMapper;
    private final CoachingService coachingService;


    /**
     * Creates a new student with a profile.
     *
     * @param studentDTO  the StudentDTO object containing the student and profile details
     * @return ResponseEntity with the created Student object and HTTP status CREATED
     */
    @PostMapping("/createStudent")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
        Student student = userService.createStudentWithProfile(studentMapper.toEntity(studentDTO));
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    /**
     * Creates a new teacher with a profile.
     *
     * @param teacherDTO  the TeacherDTO object containing the teacher and profile details
     * @return ResponseEntity with the created Teacher object and HTTP status CREATED
     */
    @PostMapping("/createTeacher")
    public ResponseEntity<Teacher> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        Teacher teacher = userService.createTeacherWithProfile(teacherMapper.toEntity(teacherDTO));
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }


    // create a new admin with profil
    @PostMapping("/createAdmin")
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminDTO adminDTO){
        Admin admin = userService.createAdminWithProfile(adminMapper.toEntity(adminDTO));
        return new ResponseEntity<>(admin, HttpStatus.CREATED);
    }


    //retrieve a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

}
