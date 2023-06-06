package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.UserTypeMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final UserTypeMapper userTypeMapper;


    /**
     * Creates a conversation between two users.
     *
     * @param userIds the IDs of the users involved in the conversation
     * @return the created Conversation object
     * @throws UserNotFoundException if at least one user is not found
     */
    public Conversation createConversation(Set<Long> userIds) {
        Set<User> users = new HashSet<>(userRepository.findAllById(userIds));;
        if (users.size() < 2) {
            throw new UserNotFoundException("At least one user not found!");
        }
        Conversation conversation = new Conversation();
        conversation.setUsers(users);

        for (User user : users) {
            user.getConversations().add(conversation);
        }

        conversationRepository.save(conversation);
        userRepository.saveAll(users);

        return conversation;
    }

}
