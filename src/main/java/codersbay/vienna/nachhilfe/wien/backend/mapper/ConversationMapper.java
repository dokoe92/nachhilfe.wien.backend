package codersbay.vienna.nachhilfe.wien.backend.mapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.messagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.messagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserTypeDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ConversationMapper {

    private final MessageMapper messageMapper;
    private final UserTypeMapper userTypeMapper;

    public ConversationDTO toDTO(Conversation conversation) {
        Set<MessageDTO> messageDTOS = conversation.getMessages().stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toSet());
        Set<UserTypeDTO> userTypeDTOS = conversation.getUsers().stream()
                .map(userTypeMapper::toDTO)
                .collect(Collectors.toSet());
        return new ConversationDTO(conversation.getId(),  userTypeDTOS, messageDTOS );
    }
}
