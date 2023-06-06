package codersbay.vienna.nachhilfe.wien.backend.dto.messagedto;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserTypeDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDTO {

    private Long messageId;
    private LocalDateTime timeStamp;
    private Long conversationId;
    private String title;
    private String content;
    private Long senderId;


}
