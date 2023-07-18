package codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto;

import codersbay.vienna.nachhilfe.wien.backend.model.MessageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDTO implements Comparable<MessageDTO>{

    private Long id;
    private LocalDateTime timeStamp;
    private Long conversationId;
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Long senderId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Override
    public int compareTo(MessageDTO other) {
        return this.id.compareTo(other.id);
    }

}
