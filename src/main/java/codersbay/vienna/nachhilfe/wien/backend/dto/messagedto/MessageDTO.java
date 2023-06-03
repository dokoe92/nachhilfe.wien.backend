package codersbay.vienna.nachhilfe.wien.backend.dto.messagedto;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

    private long messageId;
    private String title;
    private String content;
    private UserDTO sender;
}
