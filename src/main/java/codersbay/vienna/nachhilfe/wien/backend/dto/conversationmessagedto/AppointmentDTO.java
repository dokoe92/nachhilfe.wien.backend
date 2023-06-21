package codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto;


import codersbay.vienna.nachhilfe.wien.backend.model.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO extends MessageDTO{

    private Long appointmentId;
    private Long coachingId;
    private Long studentId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
    private String content;
}
