package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.studentdto.StudentDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.StudentMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherMapper;
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
    private final CoachingService coachingService;



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
