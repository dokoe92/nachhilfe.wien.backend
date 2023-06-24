package codersbay.vienna.nachhilfe.wien.backend.runner;

import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthRequest;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthResponse;
import codersbay.vienna.nachhilfe.wien.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class StartUpRunner implements CommandLineRunner {
    private final AuthService authService;
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

        /*
        AuthResponse teacherResponse = authService.authenticate(teacherRequest);
        AuthResponse studentResponse = authService.authenticate(studentRequest);

        log.trace("Teacher Token: " + teacherResponse.getToken());
        log.trace("Student Token: " + studentResponse.getToken());
*/

    }
}
