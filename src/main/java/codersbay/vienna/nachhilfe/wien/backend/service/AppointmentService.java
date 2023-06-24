package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.Application;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.AppointmentMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Appointment;
import codersbay.vienna.nachhilfe.wien.backend.model.Status;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment.setStatus(Status.PENDING);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDTO(savedAppointment);
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



