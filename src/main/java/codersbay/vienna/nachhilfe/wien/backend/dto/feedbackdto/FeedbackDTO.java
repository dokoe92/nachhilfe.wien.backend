package codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FeedbackDTO {
    private Long feedbackId;
    private Long teacherId;
    private Long studentId;
    private String studentFirstName;
    private String studentLastName;
    private String teacherFirstName;
    private String teacherLastName;
    private String title;
    private String content;
    private Integer rating;
    private LocalDate date;
}
