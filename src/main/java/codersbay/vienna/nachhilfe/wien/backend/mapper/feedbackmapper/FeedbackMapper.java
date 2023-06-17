package codersbay.vienna.nachhilfe.wien.backend.mapper.feedbackmapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Feedback;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.service.StudentService;
import codersbay.vienna.nachhilfe.wien.backend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackMapper {

    private final TeacherService teacherService;
    private final StudentService studentService;

    public FeedbackDTO toDTO(Feedback feedback) {
        if (feedback == null) {
            return null;
        }

        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackId(feedback.getId());
        feedbackDTO.setStudentId(feedback.getStudent().getId());
        feedbackDTO.setTeacherId(feedback.getTeacher().getId());
        feedbackDTO.setStudentFirstName(feedback.getStudent().getFirstName());
        feedbackDTO.setTitle(feedback.getTitle());
        feedbackDTO.setContent(feedback.getContent());
        feedbackDTO.setRating(feedback.getRating());
        feedbackDTO.setDate(feedback.getDate());

        return feedbackDTO;
    }

    public Feedback toEntity(FeedbackDTO feedbackDTO, Teacher teacher, Student student) {
        if (feedbackDTO == null || teacher == null || student == null) {
            return null;
        }
        Feedback feedback = new Feedback();
        feedback.setTeacher(teacher);
        feedback.setStudent(student);
        feedback.setTitle(feedbackDTO.getTitle());
        feedback.setContent(feedbackDTO.getContent());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setDate(feedbackDTO.getDate());

        return feedback;
    }

}
