package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.*;
import codersbay.vienna.nachhilfe.wien.backend.model.Pojo.Authentication.AuthRequest;
import codersbay.vienna.nachhilfe.wien.backend.model.Pojo.Authentication.AuthResponse;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.AuthController;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ProfileNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Finds a user profile by email and password.
     * This method must be reworked because the authentication shall be handled via spring security
     *
     * @param email    the email of the profile
     * @param password the password of the profile
     * @return an AuthResponse object containing the user's authentication details
     * @throws ProfileNotFoundException if no profile is found with the given email and password
     */


    public AuthResponse findByEmailAndPassword(String email, String password) {
        Profile profile = profileRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ProfileNotFoundException("No profile found with this email and password"));
        User user = profile.getUser();
        AuthResponse authResponse = new AuthResponse();

        authResponse.setUserId(profile.getId());
        authResponse.setAccessToken("1234");
        authResponse.setEmail(profile.getEmail());
        authResponse.setFirstName(user.getFirstName());
        authResponse.setLastName(user.getLastName());
        authResponse.setType(user.getUserType());

        return authResponse;
    }

    public AuthResponse createTeacherWithProfile(Teacher teacher) {
        Profile profile = teacher.getProfile();
        profile.setPassword(passwordEncoder.encode(teacher.getPassword()));
        profileRepository.save(profile);
        teacher.setProfile(profile);
        teacher.setRole(Role.ROLE_TEACHER);
        userRepository.save(teacher);

        AuthResponse auth = new AuthResponse();
        auth.setUserId(teacher.getId());
        auth.setType(teacher.getUserType());
        auth.setEmail(profile.getEmail());
        auth.setFirstName(teacher.getFirstName());
        auth.setLastName(teacher.getLastName());

        String jwtToken = jwtService.generateToken(teacher);
        auth.setAccessToken(jwtToken);

        return auth;
    }

    public AuthResponse createStudentWithProfile(Student student) {
        Profile profile = student.getProfile();
        profile.setPassword(passwordEncoder.encode(student.getPassword()));
        profileRepository.save(profile);
        student.setProfile(profile);
        student.setRole(Role.ROLE_STUDENT);
        userRepository.save(student);

        AuthResponse auth = new AuthResponse();
        auth.setUserId(student.getId());
        auth.setType(student.getUserType());
        auth.setEmail(profile.getEmail());
        auth.setFirstName(student.getFirstName());
        auth.setLastName(student.getLastName());

        String jwtToken = jwtService.generateToken(student);
        auth.setAccessToken(jwtToken);


        return auth;
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = profileRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"))
                .getUser();

        AuthResponse auth = new AuthResponse();
        auth.setUserId(user.getId());
        auth.setType(user.getUserType());
        auth.setEmail(user.getProfile().getEmail());
        auth.setFirstName(user.getFirstName());
        auth.setLastName(user.getLastName());

        String jwtToken = jwtService.generateToken(user);
        auth.setAccessToken(jwtToken);

        return auth;
    }
}
