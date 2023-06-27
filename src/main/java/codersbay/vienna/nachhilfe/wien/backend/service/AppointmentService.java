package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.AppointmentMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;

    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, Long studentId, Long userId) {

        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (!(user instanceof Student)) {
            throw new UserNotAuthorizedException("User must be a Student!");
        }

        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment.setStudent((Student) user);
        appointment.setStatus(Status.PENDING);
        appointmentRepository.save(appointment);

        appointmentDTO.setStatus(Status.PENDING);


        return appointmentDTO;
    }

    @Transactional
    public AppointmentDTO confirmAppointment(Long appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        appointment.setStatus(Status.CONFIRMED);
        return appointmentMapper.toDTO(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentDTO rejectAppointment(Long appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        appointment.setStatus(Status.REJECTED);
        return appointmentMapper.toDTO(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentDTO rescheduleAppointment(Long appointmentId, AppointmentDTO appointmentDTO) {
        Appointment appointment = findAppointmentById(appointmentId);
        appointment.setStart(appointmentDTO.getStart());
        appointment.setEnd(appointmentDTO.getEnd());
        return appointmentMapper.toDTO(appointmentRepository.save(appointment));
    }

    private Appointment findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment Id: " + appointmentId));
    }
}



