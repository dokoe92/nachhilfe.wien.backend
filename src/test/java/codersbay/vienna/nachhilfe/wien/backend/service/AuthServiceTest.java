package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherCreationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDistricts;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Set;

import static codersbay.vienna.nachhilfe.wien.backend.model.Districts.DISTRICT_1010;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthServiceTest {

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


        System.out.println(teacherDistricts.getTeacherId() + " districts " + teacherDistricts.getDistricts());
        System.out.println(headers);
        HttpEntity<TeacherDistricts> request = new HttpEntity<>(teacherDistricts, headers );

        ResponseEntity<TeacherDistricts> responseEntity =
                testRestTemplate.exchange("/teacher/updateDistricts/"+ teacherId, HttpMethod.PUT, request, TeacherDistricts.class );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


}