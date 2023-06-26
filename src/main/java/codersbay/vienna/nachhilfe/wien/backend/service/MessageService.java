package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.AppointmentMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.MessageMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.MessageRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MessageService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final AppointmentMapper appointmentMapper;
    private final CoachingRepository coachingRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;


    /**
     * Sends a message to a conversation.
     *
     * @param messageDTO      the MessageDTO object containing the message details
     * @param conversationId  the ID of the conversation which will be submitted via the path variable in the rest-endpoint
     * @return the MessageDTO object with the sent message details
     * @throws ResourceNotFoundException if the conversation or the message is not found
     */
    public MessageDTO sendMessage(MessageDTO messageDTO, Long conversationId) {
        Optional<Conversation> conversation = conversationRepository.findById(conversationId);
        if (conversation.isEmpty()) {
            throw new ResourceNotFoundException("No conversation found!");
        }
        messageDTO.setConversationId(conversationId);
        Set<Message> messages = conversation.get().getMessages();
        Message message = messageMapper.toEntity(messageDTO);
        message.setConversation(conversation.get());
        messages.add(message);
        conversation.get().setMessages(messages);

        Message savedMessage = messageRepository.save(message);
        savedMessage = messageRepository.findById(savedMessage.getId()).orElseThrow(() -> new ResourceNotFoundException("Message not found!"));
        conversationRepository.save(conversation.get());


        messageDTO.setTimeStamp(savedMessage.getTimestamp());
        messageDTO.setMessageId(message.getId());
        messageDTO.setMessageType(message.getMessageType());

        return messageDTO;
    }

    public AppointmentDTO sendAppointment(AppointmentDTO appointmentDTO, Long conversationId, Long coachingId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found!"));

        Coaching coaching = coachingRepository.findById(coachingId)
                .orElseThrow(() -> new ResourceNotFoundException("Coaching not found!"));


        if (appointmentDTO.getStudentId() == null) {
            throw new ResourceNotFoundException("User Id needed");
        }
        User user = userRepository.findById(appointmentDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (!(user instanceof Student)) {
            throw new UserNotAuthorizedException("User must be a Student!");
        }


        // Make an appointment from the DTO and set the fields
        // Sender and student fields are handled in the mapper
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment.setStudent((Student) user);
        appointment.setStatus(Status.SCHEDULED);
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
        appointmentDTO.setAppointmentId(appointment.getId());
        appointmentDTO.setCoachingId(coachingId);
        appointmentDTO.setStatus(Status.SCHEDULED);
        appointmentDTO.setMessageType(MessageType.APPOINTMENT);
        appointmentDTO.setConfirmed(false);

        return appointmentDTO;
    }

    public Status changeAppointmentStatus(Status status, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found!"));

        appointment.setStatus(status);
        appointmentRepository.save(appointment);
        return appointment.getStatus();
    }

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
}
