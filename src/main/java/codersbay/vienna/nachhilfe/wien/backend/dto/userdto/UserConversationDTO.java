package codersbay.vienna.nachhilfe.wien.backend.dto.userdto;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class UserConversationDTO {

    Long userId;
    UserTypeDTO userType;
    Set<ConversationDTO> conversations = new HashSet<>();
}
