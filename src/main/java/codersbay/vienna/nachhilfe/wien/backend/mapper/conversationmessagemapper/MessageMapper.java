package codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.UserTypeMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Message;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Table(name="message")
public class MessageMapper {

    private final UserTypeMapper userTypeMapper;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    public MessageDTO toDTO(Message message) {
        if (message == null) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setMessageId(message.getId());
        messageDTO.setTitle(message.getTitle());
        messageDTO.setContent(message.getContent());
        messageDTO.setSenderId(message.getSender().getId());
        messageDTO.setConversationId(message.getConversations().getId());

        return messageDTO;
    }

    public Message toEntity(MessageDTO messageDTO) {
        if (messageDTO == null) {
            return null;
        }

        Message message = new Message();

        message.setTitle(messageDTO.getTitle());
        message.setContent(messageDTO.getContent());

        Optional<User> sender = userRepository.findById(messageDTO.getSenderId());
        if (sender.isEmpty()) {
            throw new ResourceNotFoundException("User not found!");
        }
        message.setSender(sender.get());


        return message;
    }

}
