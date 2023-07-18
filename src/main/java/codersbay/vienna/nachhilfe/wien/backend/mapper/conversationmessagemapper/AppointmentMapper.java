package codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Appointment;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
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
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setTimeStamp(appointment.getTimestamp());
        appointmentDTO.setConversationId(appointment.getConversation().getId());
        appointmentDTO.setTitle(appointment.getTitle());
        appointmentDTO.setContent(appointment.getContent());
        appointmentDTO.setStudentId(appointment.getStudent().getId());
        appointmentDTO.setStart(appointment.getStart());
        appointmentDTO.setEnd(appointment.getEnd());
        appointmentDTO.setStatus(appointment.getStatus());
        appointmentDTO.setCoachingId(appointment.getCoaching().getId());
        appointmentDTO.setTeacherId(appointment.getCoaching().getUser().getId());
        appointmentDTO.setSenderId(appointment.getSender().getId());
        appointmentDTO.setMessageType(appointment.getMessageType());
        appointmentDTO.setCoachingName(String.valueOf(appointment.getCoaching().getSubject()));
        appointmentDTO.setStudentName(appointment.getStudent().getFirstName() + " " + appointment.getStudent().getLastName());
        appointmentDTO.setTeacherName(appointment.getCoaching().getUser().getFirstName() + " " + appointment.getCoaching().getUser().getLastName());

        return appointmentDTO;
    }

    public Appointment toEntity(AppointmentDTO appointmentDTO) {
        if (appointmentDTO == null) {
            return null;
        }

        Appointment appointment = new Appointment();



        appointment.setTitle(appointmentDTO.getTitle());
        appointment.setContent(appointmentDTO.getContent());
        appointment.setStart(appointmentDTO.getStart());
        appointment.setEnd(appointmentDTO.getEnd());
        appointment.setStatus(appointmentDTO.getStatus());


        return appointment;
    }



}
