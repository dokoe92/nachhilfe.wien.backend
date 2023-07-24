package codersbay.vienna.nachhilfe.wien.backend.runner;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthRequest;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class StartUpRunner implements CommandLineRunner {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final ProfileRepository profileRepository;
    private final JwtService jwtService;
    private Logger log = LoggerFactory.getLogger(StartUpRunner.class);

    /**
     *
     * @param args
     * Create tokens for teacher, student and admin out of data.sql test data
     * @throws Exception
     */


    @Override
    public void run(String... args) throws Exception {
        AuthRequest teacherRequest = new AuthRequest();
        teacherRequest.setEmail("john01@example.com");
        teacherRequest.setPassword("password");

        AuthRequest studentRequest = new AuthRequest();
        studentRequest.setEmail("student01@example.com");
        studentRequest.setPassword("password");

        AuthRequest adminRequest = new AuthRequest();
        adminRequest.setEmail("admin01@example.com");
        adminRequest.setPassword("password");

        log.trace("Teacher Token: " + generateToken(teacherRequest));
        log.trace("Student Token: " + generateToken(studentRequest));
        log.trace("Admin Token: " + generateToken(adminRequest));

    }

    private String generateToken(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        User user = profileRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"))
                .getUser();

        return jwtService.generateToken(user);
    }
}
