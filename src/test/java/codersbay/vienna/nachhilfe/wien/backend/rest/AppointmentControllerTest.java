package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.Application;
import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static codersbay.vienna.nachhilfe.wien.backend.model.Role.ROLE_TEACHER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentControllerTest extends AbstractControllerTest {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private static TransactionTemplate transactionTemplate;

    @Autowired
    private CoachingRepository coachingRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void ping() throws Exception {

        final Profile profile = Profile.builder().password("secret").email("profile@gmail.com").build();
        profileRepository.save(profile);

        final Teacher teacher = Teacher.builder().profile(profile).firstName("Max").lastName("Mustermann").role(ROLE_TEACHER).build();
        teacherRepository.save(teacher);

        assertEquals(1L, profileRepository.count());
        assertEquals(1L, teacherRepository.count());

        final String URL = "/appointment/ping";
        /*
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getToken(teacher));*/

        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, new URI(URL)));
        resultActions.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    /*
    @Test
    public void testSendAppointment() {

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setContent("New appointment");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getToken(student1));

        HttpEntity<AppointmentDTO> request = new HttpEntity<>(appointmentDTO, headers);

        ResponseEntity<AppointmentDTO> responseEntity =
                testRestTemplate.exchange("/appointment/send-appointment/" + conversation1.getId() + "/" + coaching1.getId(), HttpMethod.POST, request, AppointmentDTO.class);

        AppointmentDTO appointmentDtoResponse = responseEntity.getBody();
        try {
            String json = objectMapper.writeValueAsString(appointmentDtoResponse);
            System.out.println(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }*/


    /*
    public void testGetAllAppoinmtents_AppointmentsAsStudent_ReturnAllAppointments() {

        HttpEntity<String> request = new HttpEntity<>(createAuthorizationHeader(student1));

        ParameterizedTypeReference<Set<AppointmentDTO>> responseType = new ParameterizedTypeReference<Set<AppointmentDTO>>() {
        };


        ResponseEntity<Set<AppointmentDTO>> responseEntity =
                testRestTemplate.exchange("/appointment/get-appointments/" + student1.getId(), HttpMethod.GET, request, responseType);

        Set<AppointmentDTO> response = responseEntity.getBody();
        List<AppointmentDTO> responseAsList = new ArrayList<>(response);
        responseAsList.sort(Comparator.comparing(AppointmentDTO::getTimeStamp));
        try {
            String json = objectMapper.writeValueAsString(responseAsList);
            System.out.println(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        assertEquals(3, response.size());
        assertEquals("Test1", responseAsList.get(0).getContent());
        assertEquals("Test2", responseAsList.get(1).getContent());
        assertEquals("Test3", responseAsList.get(2).getContent());
    }


    public void testGetAllAppoinmtents_AppointmentsAsTeacher_ReturnAllAppointments() {

        HttpEntity<String> request = new HttpEntity<>(createAuthorizationHeader(student1));

        ParameterizedTypeReference<Set<AppointmentDTO>> responseType = new ParameterizedTypeReference<Set<AppointmentDTO>>() {
        };


        ResponseEntity<Set<AppointmentDTO>> responseEntity =
                testRestTemplate.exchange("/appointment/get-appointments/" + teacher1.getId(), HttpMethod.GET, request, responseType);

        Set<AppointmentDTO> response = responseEntity.getBody();
        List<AppointmentDTO> responseAsList = new ArrayList<>(response);
        responseAsList.sort(Comparator.comparing(AppointmentDTO::getTimeStamp));
        try {
            String json = objectMapper.writeValueAsString(responseAsList);
            System.out.println(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        assertEquals(3, response.size());
    }*/


    private String getToken(User user) {
        return jwtService.generateToken(user);
    }

    private HttpHeaders createAuthorizationHeader(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getToken(user));

        return headers;
    }
}

