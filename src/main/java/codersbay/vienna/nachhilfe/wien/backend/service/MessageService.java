package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.AppointmentMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.MessageMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.MessageRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final AppointmentMapper appointmentMapper;
    private final CoachingRepository coachingRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;


    /**
     * Sends a message to a conversation.
     *
     * @param messageDTO      the MessageDTO object containing the message details
     * @param conversationId  the ID of the conversation which will be submitted via the path variable in the rest-endpoint
     * @return the MessageDTO object with the sent message details
     * @throws ResourceNotFoundException if the conversation or the message is not found
     */
    @Transactional
    public MessageDTO sendMessage(MessageDTO messageDTO, Long conversationId, Long userId) {
        Optional<Conversation> conversation = conversationRepository.findByIdWithUsers(conversationId);
        if (conversation.isEmpty()) {
            throw new ResourceNotFoundException("No conversation found!");
        }

        if (conversation.get().getUsers() != null) {
            boolean ok = false;
            for (User user : conversation.get().getUsers()) {
                if (user.getId().equals(userId)) {
                    ok = true;
                }
            }
            if (!ok) {
                throw new UserNotAuthorizedException("User not authorized!");
            }
        }

        messageDTO.setConversationId(conversationId);
        Set<Message> messages = conversation.get().getMessages();
        Message message = messageMapper.toEntity(messageDTO);
        message.setConversation(conversation.get());
        messages.add(message);
        conversation.get().setMessages(messages);

        Message savedMessage = messageRepository.save(message);
        savedMessage = messageRepository.findById(savedMessage.getId()).orElseThrow(() -> new ResourceNotFoundException("Message not found!"));
        conversationRepository.save(conversation.get());


        messageDTO.setTimeStamp(savedMessage.getTimestamp());
        messageDTO.setId(message.getId());
        messageDTO.setMessageType(message.getMessageType());

        return messageDTO;
    }

}
