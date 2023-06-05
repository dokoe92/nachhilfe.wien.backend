package codersbay.vienna.nachhilfe.wien.backend.dto.messagedto;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserTypeDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

    private long messageId;
    private Long conversationId;
    private String title;
    private String content;
    private UserTypeDTO sender;

}
