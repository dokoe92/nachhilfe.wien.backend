package codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDTO {

    private Long messageId;
    private Long conversationId;
    private LocalDateTime timeStamp;
    private String title;
    private String content;
    private Long senderId;
}
