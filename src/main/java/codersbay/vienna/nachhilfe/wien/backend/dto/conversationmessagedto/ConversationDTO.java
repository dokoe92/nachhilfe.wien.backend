package codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {

    private long conversationId;
    private Set<UserTypeDTO> users = new HashSet<>();
    private Set<MessageDTO> messages = new HashSet<>();
}
