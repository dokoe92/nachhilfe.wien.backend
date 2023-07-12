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
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
            if (conversationPartner instanceof Student) {
                ((Student) conversationPartner).getAppointments().add(appointment);
            }
            if (conversationPartner instanceof Teacher) {
                Set<Coaching> coachings = conversationPartner.getCoachings();
                coachings.add(coaching);
                conversationPartner.setCoachings(coachings);
                userRepository.save(conversationPartner);
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
        Set<AppointmentDTO> userAppointments = new HashSet<>();

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

    public AppointmentDTO findAppointmentById(Long appointmentId) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            return appointmentMapper.toDTO(appointment);
        }
        // Handle the case when the appointment is not found
        // You can throw an exception or return null, depending on your use case
        return null;
    }

    public List<AppointmentDTO> findAppointmentsByDate (LocalDateTime startDate) {
        List<Appointment> appointments = appointmentRepository.findByStart(startDate);
        List<AppointmentDTO> appointmentDTOS = appointments.stream()
                .map(appointmentMapper::toDTO).toList();
        return appointmentDTOS;

    }
}
