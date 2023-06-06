package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.MessageMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Message;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.MessageRepository;
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


    /**
     * Sends a message to a conversation.
     *
     * @param messageDTO      the MessageDTO object containing the message details
     * @param conversationId  the ID of the conversation which will be submitted via the path variable in the rest-endpoint
     * @return the MessageDTO object with the sent message details
     * @throws ResourceNotFoundException if the conversation or the message is not found
     */
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

        Message savedMessage = messageRepository.save(message);
        savedMessage = messageRepository.findById(savedMessage.getId()).orElseThrow(() -> new ResourceNotFoundException("Message not found!"));
        conversationRepository.save(conversation.get());


        messageDTO.setTimeStamp(savedMessage.getTimestamp());
        messageDTO.setMessageId(message.getId());

        return messageDTO;

    }


}
