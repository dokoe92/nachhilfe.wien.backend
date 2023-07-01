package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.AppointmentMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public AppointmentDTO sendAppointment(AppointmentDTO appointmentDTO, Long conversationId, Long coachingId, Long studentId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found!"));

        Coaching coaching = coachingRepository.findById(coachingId)
                .orElseThrow(() -> new ResourceNotFoundException("Coaching not found!"));

        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));


        // Make an appointment from the DTO and set the fields
        // Sender and student fields are handled in the mapper
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment.setSender(user);
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
        AppointmentDTO appointmentDTOCreated = appointmentMapper.toDTO(appointment);
        appointmentDTOCreated.setStudentId(studentId);
        appointmentDTOCreated.setStatus(Status.SCHEDULED);
        appointmentDTOCreated.setMessageType(MessageType.APPOINTMENT);
        appointmentDTOCreated.setConfirmed(false);
        return appointmentDTOCreated;
    }

//    public Status changeAppointmentStatus(Status status, Long appointmentId) {
//        Appointment appointment = appointmentRepository.findById(appointmentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found!"));
//
//        appointment.setStatus(status);
//        appointmentRepository.save(appointment);
//        return appointment.getStatus();
//    }

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

    public AppointmentDTO confirmAppointment(Long appointmentId, Long teacherId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new ResourceNotFoundException("Appointment is not existing"));

        if (!appointment.getSender().getId().equals(teacherId)) {
            throw new ResourceNotFoundException("Unauthorized to confirm this appointment");
        }

            appointment.setStatus(Status.CONFIRMED);
            appointment.setConfirmed(true);
            appointmentRepository.save(appointment);

            AppointmentDTO appointmentDTO = appointmentMapper.toDTO(appointment);
            return appointmentDTO;
        }
    }

