package codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto;


import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO {

    private Long appointmentId;
    private Long conversationId;
    private Long coachingId;
    private Long senderId;
    private Long studentId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
    private String title;
    private String content;
    private LocalDateTime timeStamp;

}
