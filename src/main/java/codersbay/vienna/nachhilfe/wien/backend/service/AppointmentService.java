package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.AppointmentMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicatedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final CoachingRepository coachingRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;
    private final StudentRepository studentRepository;


    @Transactional
    public AppointmentDTO sendAppointment(AppointmentDTO appointmentDTO, Long conversationId, Long coachingId, Long studentId) {
        Conversation conversation = conversationRepository.findByIdWithUsers(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found!"));



        Coaching coaching = coachingRepository.findById(coachingId)
                .orElseThrow(() -> new ResourceNotFoundException("Coaching not found!"));

        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        /*
        if (appointmentRepository.existsByStudentIdAndCoachingId(studentId, coachingId)) {
            throw new DuplicatedException("User already has an appointment for this coaching!");
        }*/

        if (user.getConversations() == null) {
            throw new ResourceNotFoundException("User dont have a conversation");
        }

        boolean found = false;
        for (Conversation conv : user.getConversations()) {
            if (conv.getId().equals(conversationId)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw  new UserNotAuthorizedException("User cant send to this conversation");
        }

        Set<User> conversationUsers = conversation.getUsers();

        boolean teacherHasCoaching = false;
        if (conversation.getUsers() != null) {
            for (User checkUser : conversationUsers) {
                if (checkUser instanceof Teacher) {
                    if (checkUser.getCoachings() == null) {
                        throw new UserNotAuthorizedException("This teacher doesnt own the appointment");
                    } else {
                        for (Coaching checkCoaching : checkUser.getCoachings()) {
                            if (checkCoaching.getId().equals(coachingId)) {
                                teacherHasCoaching = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!teacherHasCoaching) {
            throw new UserNotFoundException("The teacher of the mentioned conversation doesnt own this coaching!");
        }

        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Europe/Vienna"));
        if (appointmentDTO.getStart().withZoneSameInstant(ZoneId.of("Europe/Vienna")).isBefore(currentDateTime)) {
            throw new IllegalArgumentException("Appointment start before current time");
        }
        if (appointmentDTO.getEnd().withZoneSameInstant(ZoneId.of("Europe/Vienna")).isBefore(currentDateTime)) {
            throw new IllegalArgumentException("Appointmend end before current time");
        }


        // Sender and student fields are handled in the mapper
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment.setSender(user);
        appointment.setStudent((Student) user);
        appointment.setStatus(Status.PENDING);
        appointment.setCoaching(coaching);
        appointment.setConversation(conversation);
        appointment.setConfirmed(false);
        appointmentRepository.save(appointment);

        // Add message to the conversation
        Set<Message> messages = conversation.getMessages();
        messages.add(appointment);
        conversation.setMessages(messages);
        conversationRepository.save(conversation);

        // If student add appointment to appointment field, if teacher add appointment to coaching
        Set<User> conversationPartners = conversation.getUsers();
        for (User conversationPartner : conversationPartners) {
            if (conversationPartner instanceof Student) {
                ((Student) conversationPartner).getAppointments().add(appointment);
                studentRepository.save((Student) conversationPartner);
            }
            if (conversationPartner instanceof Teacher) {
                coaching.getAppointments().add(appointment);
                coachingRepository.save(coaching);
            }
        }
        // Set all DTO fields
        AppointmentDTO appointmentDTOCreated = appointmentMapper.toDTO(appointment);
        appointmentDTOCreated.setStudentId(studentId);
        appointmentDTOCreated.setStatus(Status.PENDING);
        appointmentDTOCreated.setMessageType(MessageType.APPOINTMENT);
        appointmentDTOCreated.setConfirmed(false);
        return appointmentDTOCreated;
    }

    @Transactional(readOnly = true)
    public Set<AppointmentDTO> getAllAppointments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Set<Appointment> appointments = new HashSet<>();
        if (user instanceof Teacher) {
            Set<Coaching> coachings = user.getCoachings();
            if (coachings != null) {
                for (Coaching coaching : coachings) {
                    appointments.addAll((coaching.getAppointments()));
                }
            }
        }
        if (user instanceof Student) {
            appointments.addAll(((Student) user).getAppointments());
        }

        List<Appointment> sortedAppointments = new ArrayList<>(appointments);
        Collections.sort(sortedAppointments);
        appointments = new LinkedHashSet<>(sortedAppointments);


        Set<AppointmentDTO> appointmentDTOS = new LinkedHashSet<>();
        for (Appointment appointment : appointments) {
            AppointmentDTO appointmentDTO = appointmentMapper.toDTO(appointment);
            appointmentDTOS.add(appointmentDTO);
        }

        return appointmentDTOS;
    }

    @Transactional
    public AppointmentDTO updateStatus(Long appointmentId, Long teacherId, String action) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment is not existing"));

        if (appointment.getCoaching() == null) {
            return null;
        }
        if (appointment.getCoaching().getUser() == null) {
            return null;
        }

        if (!appointment.getCoaching().getUser().getId().equals(teacherId)) {
            throw new UserNotFoundException("Teacher not authorized");
        }

        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Europe/Vienna"));
        ZonedDateTime viennaStartTime = appointment.getStart().withZoneSameInstant(ZoneId.of("Europe/Vienna"));



        if (appointment.getStatus() == Status.REJECTED || viennaStartTime.isBefore(currentDateTime)) {
            return null;
        }

        if (action.equalsIgnoreCase("confirm")) {
            appointment.setStatus(Status.CONFIRMED);
            appointment.setConfirmed(true);
        } else if (action.equalsIgnoreCase("reject")) {
            appointment.setStatus(Status.REJECTED);
            appointment.setConfirmed(false);
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }

        appointmentRepository.save(appointment);

        AppointmentDTO appointmentDTO = appointmentMapper.toDTO(appointment);

        return appointmentDTO;
    }


    public List<AppointmentDTO> findAppointmentsByDate (LocalDateTime startDate) {
        List<Appointment> appointments = appointmentRepository.findByStart(startDate);
        List<AppointmentDTO> appointmentDTOS = appointments.stream()
                .map(appointmentMapper::toDTO).toList();
        return appointmentDTOS;

    }
}
