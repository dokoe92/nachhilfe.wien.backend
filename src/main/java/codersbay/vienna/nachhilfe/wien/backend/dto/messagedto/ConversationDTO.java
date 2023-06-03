package codersbay.vienna.nachhilfe.wien.backend.dto.messagedto;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ConversationDTO {

    private long conversationId;
    private Set<UserDTO> users;
    private Set<MessageDTO> messages;
}
