package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.Application;
import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
        import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.AppointmentMapper;
        import codersbay.vienna.nachhilfe.wien.backend.model.*;
        import codersbay.vienna.nachhilfe.wien.backend.respository.*;
        import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.service.AppointmentService;
import codersbay.vienna.nachhilfe.wien.backend.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.Mock;
        import org.mockito.junit.jupiter.MockitoExtension;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
        import java.util.Optional;
        import java.util.Set;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles("dev")
class AppointmentControllerTest {

    @Autowired
    private CoachingRepository coachingRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TestRestTemplate testRestTemplate;


    private Profile studentProfile1;
    private Profile studentProfile2;
    private Profile studentProfile3;

    private Student student1;
    private Student student2;
    private Student student3;

    private Profile teacherProfile1;
    private Profile teacherProfile2;
    private Profile teacherProfile3;

    private Teacher teacher1;
    private Teacher teacher2;
    private Teacher teacher3;

    private Coaching coaching1;
    private Coaching coaching2;
    private Coaching coaching3;

    private Conversation conversation1;
    private Conversation conversation2;
    private Conversation conversation3;


    @BeforeEach
    void setUp() {
        appointmentService = new AppointmentService(coachingRepository, conversationRepository, userRepository, appointmentMapper, appointmentRepository);

        // Create Student
        studentProfile1 = new Profile();
        studentProfile2 = new Profile();
        studentProfile3 = new Profile();

        studentProfile1.setEmail("student1@gmail.com");
        studentProfile2.setEmail("student2@gmail.com");
        studentProfile3.setEmail("student3@gmail.com");

        studentProfile1.setPassword("1234");
        studentProfile2.setPassword("1234");
        studentProfile3.setPassword("1234");

        profileRepository.save(studentProfile1);
        profileRepository.save(studentProfile2);
        profileRepository.save(studentProfile3);

        student1 = new Student();
        student2 = new Student();
        student3 = new Student();

        student1.setProfile(studentProfile1);
        student2.setProfile(studentProfile2);
        student3.setProfile(studentProfile3);

        student1.setRole(Role.ROLE_STUDENT);
        student2.setRole(Role.ROLE_STUDENT);
        student3.setRole(Role.ROLE_STUDENT);

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);


        // Create Teacher
        teacherProfile1 = new Profile();
        teacherProfile2 = new Profile();
        teacherProfile3 = new Profile();

        teacherProfile1.setEmail("teacher1@gmail.com");
        teacherProfile2.setEmail("teacher2@gmail.com");
        teacherProfile3.setEmail("teacher3@gmail.com");

        teacherProfile1.setPassword("1234");
        teacherProfile2.setPassword("1234");
        teacherProfile3.setPassword("1234");

        profileRepository.save(teacherProfile1);
        profileRepository.save(teacherProfile2);
        profileRepository.save(teacherProfile3);

        teacher1 = new Teacher();
        teacher2 = new Teacher();
        teacher3 = new Teacher();

        teacher1.setProfile(teacherProfile1);
        teacher2.setProfile(teacherProfile2);
        teacher3.setProfile(teacherProfile3);

        teacher1.setRole(Role.ROLE_TEACHER);
        teacher2.setRole(Role.ROLE_TEACHER);
        teacher3.setRole(Role.ROLE_TEACHER);


        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);
        teacherRepository.save(teacher3);

        // Add Coaching to teachers
        coaching1 = new Coaching();
        coaching2 = new Coaching();
        coaching3 = new Coaching();

        coaching1.setSubject(Subject.MATHEMATIK);
        coaching2.setSubject(Subject.DEUTSCH);
        coaching3.setSubject(Subject.ENGLISCH);

        coaching1.setUser(teacher1);
        coaching2.setUser(teacher2);
        coaching3.setUser(teacher3);

        // Create conversations
        conversation1 = new Conversation();
        conversation2 = new Conversation();
        conversation3 = new Conversation();

        conversation1.setUsers(Set.of(student1, teacher1));
        teacher1.setConversations(Set.of(conversation1));
        student1.setConversations(Set.of(conversation1));

        conversation2.setUsers(Set.of(student2, teacher2));
        teacher2.setConversations(Set.of(conversation2));
        student2.setConversations(Set.of(conversation2));

        conversation3.setUsers(Set.of(student3, teacher3));
        teacher3.setConversations(Set.of(conversation3));
        student3.setConversations(Set.of(conversation3));

        // Persist

        coachingRepository.save(coaching1);
        coachingRepository.save(coaching2);
        coachingRepository.save(coaching3);

        conversationRepository.save(conversation1);
        conversationRepository.save(conversation2);
        conversationRepository.save(conversation3);

    }

    private String getToken(User user) {
        return jwtService.generateToken(user);
    }


    @Test
    public void testSendAppointment() {
        // when
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setContent("New appointment");

        HttpHeaders  headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getToken(student1));

        HttpEntity<AppointmentDTO> request = new HttpEntity<>(appointmentDTO, headers);

        ResponseEntity<AppointmentDTO> responseEntity =
                testRestTemplate.exchange("/appointment/send-appointment/" + conversation1.getId() + "/" + coaching1.getId(), HttpMethod.POST, request, AppointmentDTO.class);

        AppointmentDTO appointmentDtoResponse = responseEntity.getBody();
        try {
            String json = objectMapper.writeValueAsString(appointmentDtoResponse);
            System.out.println(json);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }

    }
}

