package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
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

    @PostMapping("/createProfile")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        userService.createProfile(profile);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }
    @PostMapping("/createStudent")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        userService.createStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PostMapping("/createTeacher")
    public ResponseEntity<Teacher> createStudent(@RequestBody Teacher teacher) {
        userService.createTeacher(teacher);
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @PutMapping("/connect/{userId}/{profileId}")
    public ResponseEntity<Optional<User>> connectProfileWithUser(@PathVariable Long userId, @PathVariable Long profileId) {
        userService.connectProfileWithUser(userId, profileId);
        Optional<User> student = userService.findById(userId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }


}
