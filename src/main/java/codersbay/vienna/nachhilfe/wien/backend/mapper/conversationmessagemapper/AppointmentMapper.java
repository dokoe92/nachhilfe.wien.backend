package codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Appointment;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.MessageRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppointmentMapper {

    public final ConversationRepository conversationRepository;
    public final UserRepository userRepository;
    public final StudentRepository studentRepository;
    public final CoachingRepository coachingRepository;



    public AppointmentDTO toDTO (Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(appointment.getId());
        appointmentDTO.setTimeStamp(appointment.getTimestamp());
        appointmentDTO.setConversationId(appointment.getConversation().getId());
        appointmentDTO.setTitle(appointment.getTitle());
        appointmentDTO.setContent(appointment.getContent());
        appointmentDTO.setSenderId(appointment.getSender().getId());
        appointmentDTO.setStart(appointment.getStart());
        appointmentDTO.setEnd(appointment.getEnd());
        appointmentDTO.setStatus(appointment.getStatus());
        appointmentDTO.setCoachingId(appointment.getCoaching().getId());
        appointmentDTO.setSenderId(appointment.getStudent().getId());

        return appointmentDTO;
    }

    public Appointment toEntity(AppointmentDTO appointmentDTO) {
        if (appointmentDTO == null) {
            return null;
        }

        Appointment appointment = new Appointment();


        Student student = studentRepository.findById(appointmentDTO.getStudentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found!"));

        appointment.setTitle(appointmentDTO.getTitle());
        appointment.setContent(appointmentDTO.getContent());
        appointment.setSender(student);
        appointment.setStart(appointmentDTO.getStart());
        appointment.setEnd(appointmentDTO.getEnd());
        appointment.setStatus(appointmentDTO.getStatus());
        appointment.setStudent(student);

        return appointment;
    }



}
