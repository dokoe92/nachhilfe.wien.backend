package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.studentdto.StudentPublicDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherPublicDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.ProfileDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.studentmapper.StudentPublicMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherPublicMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.ProfileMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.service.ImageService;
import codersbay.vienna.nachhilfe.wien.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final ProfileMapper profileMapper;
    private final JwtService jwtService;
    private final ImageService imageService;
    private final TeacherPublicMapper teacherPublicMapper;
    private final StudentPublicMapper studentPublicMapper;

    @GetMapping("/{id}")
    @Operation(

    )
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            if (foundUser instanceof Teacher) {
                Teacher teacher = (Teacher) foundUser;
                TeacherPublicDTO teacherDto = teacherPublicMapper.toDTO(teacher);
                return new ResponseEntity<>(teacherDto, HttpStatus.OK);
            } else if (foundUser instanceof Student) {
                Student student = (Student) foundUser;
                StudentPublicDTO studentDto = studentPublicMapper.toDTO(student);
                return new ResponseEntity<>(studentDto, HttpStatus.OK);
            } else {
                throw new UserNotFoundException("User not a student or teacher");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }


    @PostMapping("/picture/{id}")
    @Operation(
            summary = "Update a user's profile picture"
    )
    public ResponseEntity<ProfileDTO> setProfilePicture(@PathVariable Long id,
                                                        @RequestBody Map<String, String> imageData,
                                                        HttpServletRequest request) {


        String imageBase64 = imageData.get("image" );
        imageService.checkBase64(imageBase64, 3000000);

        User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException("User with the id " + id + " not found."));

        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);
        if (!userId.equals(id)) {
            throw new UserNotAuthorizedException("User not authorized!");
        }

        userService.updateProfilePicture(userId, imageBase64);
        return new ResponseEntity<>(profileMapper.toDTO(user.getProfile()), HttpStatus.OK);
    }

    @PutMapping("delete/{userId}")
    public ResponseEntity<Boolean> softDeleteUser(@PathVariable Long userId, HttpServletRequest request) {
        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long id = jwtService.extractUserId(token);
        if (!userId.equals(id)) {
            throw new UserNotAuthorizedException("User not authorized!");
        }

        boolean softDeleted = userService.softDeleteUser(userId);
        return new ResponseEntity<>(softDeleted, HttpStatus.NO_CONTENT);
    }


}