package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.messagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.respository.message.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;



}
