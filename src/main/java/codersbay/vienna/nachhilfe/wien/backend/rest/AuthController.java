package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.admindto.AdminDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthRequest;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthResponse;
import codersbay.vienna.nachhilfe.wien.backend.dto.studentdto.StudentCreationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherCreationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.StudentMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.adminmapper.AdminMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name="Authentication")
public class AuthController {

    private final AuthService authService;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AdminMapper adminMapper;

    /**
     * Creates a new student with a profile.
     *
     * @param studentDTO  the StudentDTO object containing the student and profile details
     * @return ResponseEntity with the created Student object and HTTP status CREATED
    */

    @PostMapping("/create-student")
    @Operation(
            description = "Create a student and link a profile"
    )
    public ResponseEntity<AuthResponse> createStudent(@Valid @RequestBody StudentCreationDTO studentDTO) {
        AuthResponse auth = authService.createStudentWithProfile(studentMapper.toEntity(studentDTO));
        return new ResponseEntity<>(auth, HttpStatus.CREATED);
    }

    /**
     * Creates a new teacher with a profile.
     *
     * @param teacherDTO  the TeacherDTO object containing the teacher and profile details
     * @return ResponseEntity with the created Teacher object and HTTP status CREATED
     */
    @PostMapping("/create-teacher")
    @Operation(
            description = "Create a teacher and link a profile"
    )
    public ResponseEntity<AuthResponse> createTeacher(@Valid @RequestBody TeacherCreationDTO teacherDTO) {
        AuthResponse auth = authService.createTeacherWithProfile(teacherMapper.toEntity(teacherDTO));
        return new ResponseEntity<>(auth, HttpStatus.CREATED);
    }

    @PostMapping("/create-admin")
    @Operation(
            description = "Create an admin and link a profile"
    )
    public ResponseEntity<AuthResponse> createAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        AuthResponse auth = authService.createAdminWithProfile(adminMapper.toEntity(adminDTO));
        return new ResponseEntity<>(auth, HttpStatus.CREATED);
    }

    @PostMapping
    @Operation(
            description = "Login via email and password"
    )
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/info")

    @Operation(
            description = "Send a Bearer Token and get all personal user information"
    )
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
            authResponse.setToken(jwtService.generateToken(profile.getUser()));
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
