package codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserTypeDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.ConversationMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.MessageMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConversationMapper {

    private final UserTypeMapper userTypeMapper;
    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;
    public UserConversationDTO toDTO(User user) {
        UserTypeDTO userTypeDTO = userTypeMapper.toDTO(user);
        Set<ConversationDTO> conversations = user.getConversations().stream()
                .map(conversationMapper::toDTO)
                .collect(Collectors.toSet());
        return new UserConversationDTO(userTypeMapper.toDTO(user), conversations);
    }

}


