package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.Application;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.AppointmentMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Appointment;
import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("dev")
class AppointmentServiceTest {
    @Mock
    private CoachingRepository coachingRepository;
    @Mock
    private ConversationRepository conversationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AppointmentMapper appointmentMapper;
    @Mock
    private AppointmentRepository appointmentRepository;

    private AppointmentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new AppointmentService(coachingRepository, conversationRepository, userRepository, appointmentMapper, appointmentRepository);
    }



    @Test
    void testGetAllAppointments_UserWithOneCoaching_ReturnAllAppointments() {
        //given
        Long userId = 1L;
        Student student = new Student();
        Set<Coaching> coachings = new HashSet<>();
        Set<Appointment> appointments = new HashSet<>();

        Coaching coaching = new Coaching();

        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();

        coachings.add(coaching);
        appointments.add(appointment1);
        appointments.add(appointment2);

        student.setCoachings(coachings);
        coaching.setAppointments(appointments);

        AppointmentDTO appointmentDTO1 = new AppointmentDTO();
        AppointmentDTO appointmentDTO2 = new AppointmentDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(student));
        when(appointmentMapper.toDTO(appointment1)).thenReturn(appointmentDTO1);
        when(appointmentMapper.toDTO(appointment2)).thenReturn(appointmentDTO2);

        //when
        Set<AppointmentDTO> result = underTest.getAllAppointments(userId);

        //then
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllAppointments_UserWithTwoCoachings_ReturnAllAppointments() {
        //given
        Long userId = 1L;
        Student student = new Student();
        Set<Coaching> coachings = new HashSet<>();
        Set<Appointment> appointmentsCoaching1 = new HashSet<>();
        Set<Appointment> appointmentsCoaching2 = new HashSet<>();


        Coaching coaching1 = new Coaching();
        Coaching coaching2 = new Coaching();

        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();
        Appointment appointment3 = new Appointment();
        Appointment appointment4 = new Appointment();

        coachings.add(coaching1);
        coachings.add(coaching2);
        appointmentsCoaching1.add(appointment1);
        appointmentsCoaching1.add(appointment2);
        appointmentsCoaching2.add(appointment3);
        appointmentsCoaching2.add(appointment4);

        student.setCoachings(coachings);
        coaching1.setAppointments(appointmentsCoaching1);
        coaching2.setAppointments(appointmentsCoaching2);


        AppointmentDTO appointmentDTO1 = new AppointmentDTO();
        AppointmentDTO appointmentDTO2 = new AppointmentDTO();
        AppointmentDTO appointmentDTO3 = new AppointmentDTO();
        AppointmentDTO appointmentDTO4 = new AppointmentDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(student));
        when(appointmentMapper.toDTO(appointment1)).thenReturn(appointmentDTO1);
        when(appointmentMapper.toDTO(appointment2)).thenReturn(appointmentDTO2);
        when(appointmentMapper.toDTO(appointment3)).thenReturn(appointmentDTO3);
        when(appointmentMapper.toDTO(appointment4)).thenReturn(appointmentDTO4);

        //when
        Set<AppointmentDTO> result = underTest.getAllAppointments(userId);

        //then
        assertEquals(4, result.size());
    }

}