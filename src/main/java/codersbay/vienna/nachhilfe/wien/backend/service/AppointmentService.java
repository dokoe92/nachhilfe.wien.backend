package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.AppointmentMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicatedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final CoachingRepository coachingRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public AppointmentDTO sendAppointment(AppointmentDTO appointmentDTO, Long conversationId, Long coachingId, Long studentId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found!"));

        Coaching coaching = coachingRepository.findById(coachingId)
                .orElseThrow(() -> new ResourceNotFoundException("Coaching not found!"));

        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (appointmentRepository.existsByStudentIdAndCoachingId(studentId, coachingId)) {
            throw new DuplicatedException("User already has an appointment for this coaching!");
        }

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

        boolean teacherHasCoaching = false;
        if (conversation.getUsers() != null) {
            for (User checkUser : conversation.getUsers()) {
                if (checkUser instanceof Teacher) {
                    if (checkUser.getCoachings() == null) {
                        throw new UserNotAuthorizedException("This teacher doesnt own the appointment");
                    } else {
                        for (Coaching checkCoaching : checkUser.getCoachings()) {
                            if (checkCoaching.getId().equals(coachingId)) {
                                teacherHasCoaching = true;
                            }
                        }
                    }
                }
            }
        }
        if (!teacherHasCoaching) {
            throw new UserNotFoundException("The teacher of the mentioned conversation doesnt own this coaching!");
        }

        // Make an appointment from the DTO and set the fields
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

        // Add the coaching to the users coachings
        Set<User> conversationPartners = conversation.getUsers();
        for (User conversationPartner : conversationPartners) {
            Set<Coaching> coachings = conversationPartner.getCoachings();
            coachings.add(coaching);
            conversationPartner.setCoachings(coachings);
            userRepository.save(conversationPartner);
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
        Set<AppointmentDTO> userAppointments = new HashSet<>();

        Set<Coaching> coachings = user.getCoachings();
        Set<Appointment> appointments = new HashSet<>();
        for (Coaching coaching : coachings) {
            appointments.addAll((coaching.getAppointments()));
        }

        Set<AppointmentDTO> appointmentDTOS = new HashSet<>();
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
}
