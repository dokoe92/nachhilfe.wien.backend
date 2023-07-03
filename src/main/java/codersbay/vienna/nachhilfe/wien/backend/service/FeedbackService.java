package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.feedbackmapper.FeedbackMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Feedback;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.respository.FeedbackRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicatedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        for (Feedback feedback : student.getFeedbacks()) {
            if (feedback.getTeacher().equals(teacher)) {
                throw new DuplicatedException("User already sent feedback for this teacher");
            }
        }

        Feedback feedback = feedbackMapper.toEntity(feedbackDTO, teacher, student);

        feedback.getTeacher().addFeedback(feedback);
        feedback.getStudent().addFeedback(feedback);

        feedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDTO(feedback);
    }


}
