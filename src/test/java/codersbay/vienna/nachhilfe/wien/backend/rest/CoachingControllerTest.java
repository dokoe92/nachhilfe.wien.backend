package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoachingControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CoachingRepository coachingRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProfileRepository profileRepository;

    private String getToken(User user) {
        return jwtService.generateToken(user);
    }

    public void testCreateCoaching() {
        Teacher teacher = new Teacher();
        Profile profile = new Profile();

        profile.setEmail("hansi@gmail.com");
        profile.setPassword("12345678");
        teacher.setProfile(profile);
        teacher.setRole(Role.ROLE_TEACHER);
        profileRepository.save(profile);
        teacherRepository.save(teacher);

        CoachingsDTO coachingsDTO = new CoachingsDTO();

        CoachingDTO coachingDTO1 = new CoachingDTO();
        coachingDTO1.setUserId(101L);
        coachingDTO1.setSubject(Subject.MATHEMATIK);
        coachingDTO1.setLevel("Intermediate");
        coachingDTO1.setRate(25.0);
        coachingDTO1.setActive(true);

        CoachingDTO coachingDTO2 = new CoachingDTO();
        coachingDTO2.setCoachingId(2L);
        coachingDTO2.setUserId(102L);
        coachingDTO2.setSubject(Subject.DEUTSCH);
        coachingDTO2.setLevel("Advanced");
        coachingDTO2.setRate(30.0);
        coachingDTO2.setActive(true);

        CoachingDTO coachingDTO3 = new CoachingDTO();
        coachingDTO3.setCoachingId(3L);
        coachingDTO3.setUserId(103L);
        coachingDTO3.setSubject(Subject.ENGLISCH);
        coachingDTO3.setLevel("Beginner");
        coachingDTO3.setRate(20.0);
        coachingDTO3.setActive(false);





    }
}