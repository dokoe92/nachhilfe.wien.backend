package codersbay.vienna.nachhilfe.wien.backend.dto.userdto;

import codersbay.vienna.nachhilfe.wien.backend.dto.messagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserConversationDTO {

    Long userId;
    Set<ConversationDTO> conversations;
}
