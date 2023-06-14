package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthRequest;
import codersbay.vienna.nachhilfe.wien.backend.dto.studentdto.StudentCreationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherCreationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.StudentMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherMapper;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthResponse;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

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


    @PostMapping
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/getAuth")
    public ResponseEntity<AuthResponse> getAuthResponse(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtService.extractUsername(token);
            Profile profile = profileRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found!"));

            if (!jwtService.isTokenValid(token, profile.getUser())) {
                throw new AuthenticationException("Token not valid!") {
                    @Override
                    public String getMessage() {
                        return super.getMessage();
                    }
                };
            }
            AuthResponse authResponse = authService.createAuthResponse(profile.getUser());
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } else {
            throw new AuthenticationException("Authentication Header is missing or not properly formatted!") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }


    }

}
