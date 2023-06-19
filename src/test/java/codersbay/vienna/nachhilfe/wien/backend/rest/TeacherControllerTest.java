package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherCreationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDistricts;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Set;

import static codersbay.vienna.nachhilfe.wien.backend.model.District.DISTRICT_1010;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

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
        Set<District> districts = teacher.getDisctricts();


        districts.add(DISTRICT_1010);
        teacher.setDisctricts(districts);
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