package codersbay.vienna.nachhilfe.wien.backend.mapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.messagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final UserTypeMapper userTypeMapper;

    public MessageDTO toDTO(Message message) {
        if (message == null) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setMessageId(message.getId());
        messageDTO.setTitle(message.getTitle());
        messageDTO.setContent(messageDTO.getContent());
        messageDTO.setSender(userTypeMapper.toDTO(message.getSender()));
        messageDTO.setConversationId(message.getConversations().getId());

        return messageDTO;
    }

}
