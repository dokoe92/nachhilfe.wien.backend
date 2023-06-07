package codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto;


import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Status;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long appointmentDTO;
    private LocalDateTime timeStamp;
    private Long conversationId;
    private String title;
    private String content;
    private Long senderId;

    private LocalDateTime start;

    private LocalDateTime end;

    private Status status;

    private Long coachingId;

    private Long studentId;

}
