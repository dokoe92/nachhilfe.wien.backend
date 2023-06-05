package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.messagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserTypeDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.UserTypeMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.message.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final UserTypeMapper userTypeMapper;

    public Conversation createConversation(Set<Long> userIds) {
        Set<User> users = new HashSet<>(userRepository.findAllById(userIds));;
        if (users.size() < 2) {
            throw new UserNotFoundException("At least one user not found!");
        }
        Conversation conversation = new Conversation();
        conversation.setUsers(users);
        conversationRepository.save(conversation);
        return conversation;
    }

}
