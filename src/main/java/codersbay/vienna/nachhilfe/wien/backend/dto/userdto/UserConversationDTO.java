package codersbay.vienna.nachhilfe.wien.backend.dto.userdto;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserConversationDTO {

    UserTypeDTO userTypeDTO;
    Set<ConversationDTO> conversations = new HashSet<>();
}
