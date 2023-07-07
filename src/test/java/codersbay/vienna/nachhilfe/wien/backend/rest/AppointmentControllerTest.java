package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static codersbay.vienna.nachhilfe.wien.backend.model.Role.ROLE_STUDENT;
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

        final Profile profile = createProfile("secret", "profile@gmail.com");
        final Teacher teacher = (Teacher) createUser(UserType.TEACHER, profile, "Max", "Mustermann");

        assertEquals(1L, profileRepository.count());
        assertEquals(1L, teacherRepository.count());

        final String URL = "/appointment/ping";

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getToken(teacher));

        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, new URI(URL)).headers(headers));
        resultActions.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    public void testCreateAppointment() throws Exception {

        final Profile teacherProfile = createProfile("secret1", "profile1@gmail.com");
        final Profile studentProfile = createProfile("secret2", "profile2@gmail.com");

        final Teacher teacher = (Teacher) createUser(UserType.TEACHER, teacherProfile, "Max", "Mustermann");
        final Student student = (Student) createUser(UserType.STUDENT, studentProfile, "Ilse", "Mustermann");

        // create conversation
        final Conversation conversation = new Conversation();
        conversation.setUsers(new HashSet<>(Arrays.asList(teacher, student)));
        conversationRepository.save(conversation);
        teacher.getConversations().add(conversation);
        student.getConversations().add(conversation);

        // update users
        userRepository.saveAll(Arrays.asList(teacher, student));

        // create coaching
        final Coaching coaching = new Coaching();
        coaching.setUser(teacher);
        coaching.setRate(0.01);
        coaching.setLevel("1");
        coaching.setActive(true);
        coaching.setSubject(Subject.MATHEMATIK);
        coachingRepository.save(coaching);
        flush();

        assertEquals(2L, profileRepository.count());
        assertEquals(2L, userRepository.count());
        assertEquals(1L, coachingRepository.count());

        final String URL = "/appointment/send-appointment/" + conversation.getId() + "/" + coaching.getId();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getToken(student));

        final AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setContent("Test appointment");
        System.out.println(objectMapper.writeValueAsString(appointmentDTO));

        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(URL)
                .headers(headers)
                .content(objectMapper.writeValueAsString(appointmentDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.content").value(appointmentDTO.getContent()));

        assertEquals(1L, appointmentRepository.count());
        assertEquals(appointmentDTO.getContent(), appointmentRepository.findAll().get(0).getContent());
    }

    private Profile createProfile(String password, String email) {
        final Profile profile = Profile.builder().password(password).email(email).build();
        return profileRepository.save(profile);
    }

    private User createUser(UserType userType, Profile profile, String firstName, String lastName) {
        final User user;
        if (userType == UserType.STUDENT) {
            user = Student.builder().profile(profile).firstName(firstName).lastName(lastName).role(ROLE_STUDENT).build();
        } else if (userType == UserType.TEACHER) {
            user = Teacher.builder().profile(profile).firstName(firstName).lastName(lastName).role(ROLE_TEACHER).build();
        } else {
            throw new IllegalStateException();
        }
        return userRepository.save(user);
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

