package codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto;


import codersbay.vienna.nachhilfe.wien.backend.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO extends MessageDTO {

    private Long coachingId;
    private Long teacherId;
    private Long studentId;
    private String coachingName;
    private String teacherName;
    private String studentName;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
    private Boolean confirmed;
}
