package codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FeedbackDTO implements Comparable<FeedbackDTO>{
    private Long feedbackId;
    private Long teacherId;
    private Long studentId;
    private String studentFirstName;
    private String studentLastName;
    private String teacherFirstName;
    private String teacherLastName;
    private String title;
    private String content;
    @NotNull
    private Integer rating;
    private LocalDate date;

    @Override
    public int compareTo(FeedbackDTO o) {
        return Long.compare(this.getFeedbackId(), o.getFeedbackId());
    }
}
