package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.Application;
import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDistricts;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Districts;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Role;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static codersbay.vienna.nachhilfe.wien.backend.model.Districts.DISTRICT_1010;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles("dev")
class TeacherControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private String getToken(User user) {
        return jwtService.generateToken(user);
    }

    @Test
    public void testUpdateTeacherDistricts(){
        Teacher teacher = new Teacher();
        Profile profile = new Profile();
        profile.setEmail("hansi@gmail.com");
        profile.setPassword("12345678");
        teacher.setProfile(profile);
        teacher.setRole(Role.ROLE_TEACHER);
        profileRepository.save(profile);
        teacherRepository.save(teacher);

        Long teacherId = teacher.getId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getToken(teacher));

        TeacherDistricts teacherDistricts = new TeacherDistricts();
        teacherDistricts.setTeacherId(teacher.getId());
        Set<Districts> districts = teacher.getDistricts();


        districts.add(DISTRICT_1010);
        teacher.setDistricts(districts);
        teacherDistricts.getDistricts().addAll(districts);

        HttpEntity<TeacherDistricts> request = new HttpEntity<>(teacherDistricts, headers );

        ResponseEntity<TeacherDistricts> responseEntity =
                testRestTemplate.exchange("/teacher/update-districts/"+ teacherId, HttpMethod.PUT, request, TeacherDistricts.class );

        TeacherDistricts teacherDistrictsResponse = responseEntity.getBody();;
        try {
            String json = objectMapper.writeValueAsString(teacherDistrictsResponse);
            System.out.println(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}