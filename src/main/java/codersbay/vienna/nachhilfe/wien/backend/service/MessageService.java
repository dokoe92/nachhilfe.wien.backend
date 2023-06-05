package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.messagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.MessageMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.model.Message;
import codersbay.vienna.nachhilfe.wien.backend.respository.message.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.message.MessageRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MessageService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;


    public MessageDTO sendMessage(MessageDTO messageDTO, Long conversationId) {
        Optional<Conversation> conversation = conversationRepository.findById(conversationId);
        if (conversation.isEmpty()) {
            throw new ResourceNotFoundException("No conversation found!");
        }
        messageDTO.setConversationId(conversationId);
        Set<Message> messages = conversation.get().getMessages();
        Message message = messageMapper.toEntity(messageDTO);
        messages.add(message);
        conversation.get().setMessages(messages);

        messageRepository.save(message);
        conversationRepository.save(conversation.get());

        messageDTO.setMessageId(message.getId());

        return messageDTO;

    }


}
