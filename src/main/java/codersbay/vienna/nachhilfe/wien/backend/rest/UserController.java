package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.StudentDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.StudentMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
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
    private final CoachingService coachingService;


    @PostMapping("/createStudent")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
        Student student = userService.createStudentWithProfile(studentMapper.toEntity(studentDTO));
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PostMapping("/createTeacher")
    public ResponseEntity<Teacher> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        Teacher teacher = userService.createTeacherWithProfile(teacherMapper.toEntity(teacherDTO));
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @PostMapping("/coaching/{userId}")
    public ResponseEntity<Coaching> createCoaching(@RequestBody CoachingRequest coachingRequest, @PathVariable Long userId) {
        Coaching coaching = coachingService.createCoaching(coachingRequest, userId);
        return new ResponseEntity<>(coaching, HttpStatus.CREATED);
    }


}
