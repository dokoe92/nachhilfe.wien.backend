package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.feedbackmapper.FeedbackMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.FeedbackRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicatedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Transactional
    public FeedbackDTO sendFeedback(FeedbackDTO feedbackDTO) {
        Teacher teacher = teacherService.findTeacherById(feedbackDTO.getTeacherId());
        if (teacher == null) {
            throw new ResourceNotFoundException("Teacher with ID " + feedbackDTO.getTeacherId() + " not found");
        }

        Student student = studentService.findStudentById(feedbackDTO.getStudentId());
        if (student == null) {
            throw new ResourceNotFoundException("Student with ID " + feedbackDTO.getStudentId() + " not found");
        }

        boolean hadAppointment = false;
        if (student.getAppointments() == null) {
            throw new ResourceNotFoundException("No appointments found!");
        } else {
            for (Appointment appointment : student.getAppointments()) {
                if (appointment.getCoaching().getUser().equals(teacher)) {
                    if (appointment.getStatus() == Status.CONFIRMED) {
                        ZonedDateTime appointmentViennaTime = appointment.getEnd().withZoneSameInstant(ZoneId.of("Europe/Vienna"));
                        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Europe/Vienna"));
                        if (appointmentViennaTime.isBefore(currentDateTime)) {
                            hadAppointment = true;
                            break;
                        }
                    }
                }
            }
        }
        if (!hadAppointment) {
            throw new IllegalArgumentException("User had no appointments with the teacher or appointment was not confirmed.");
        }

        if (student.getFeedbacks() != null) {
            for (Feedback feedback : student.getFeedbacks()) {
                if (feedback.getTeacher().equals(teacher)) {
                    throw new DuplicatedException("User already sent feedback for this teacher");
                }
            }
        }

        Feedback feedback = feedbackMapper.toEntity(feedbackDTO, teacher, student);

        feedback.getTeacher().addFeedback(feedback);
        feedback.getStudent().addFeedback(feedback);

        feedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDTO(feedback);
    }


}
