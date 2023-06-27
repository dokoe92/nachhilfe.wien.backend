package codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto;

import codersbay.vienna.nachhilfe.wien.backend.model.MessageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDTO {

    private Long id;
    private LocalDateTime timeStamp;
    private Long conversationId;
    private String title;
    private String content;
    private Long senderId;
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
}
