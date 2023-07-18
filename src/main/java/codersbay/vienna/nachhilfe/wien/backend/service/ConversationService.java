package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.ConversationMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.UserConversationMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ConversationMapper conversationMapper;
    private final UserConversationMapper userConversationMapper;

    /**
     * Creates a conversation between two users.
     *
     * @param userIds the IDs of the users involved in the conversation
     * @return the created Conversation object
     * @throws UserNotFoundException if at least one user is not found
     */
    @Transactional
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

    // DELETE ???
    public ConversationDTO findConversationById(Long id) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found!"));
        return conversationMapper.toDTO(conversation);
    }

    public UserConversationDTO findConversationsOfUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        return userConversationMapper.toDTO(user);
    }


    /**
     * Finds a conversation between two users identified by their IDs.
     *
     * @param id1 Id of the first user.
     * @param id2 Id of the second user.
     * @return An Optional containing the Conversation if one exists between the two users. If no such conversation exists, an empty Optional is returned.
     * @throws IllegalArgumentException if either of the users is not present.
     */
    public Optional<Conversation> findConversationOfUsers(Long id1, Long id2) throws IllegalArgumentException {
        User user1 = userRepository.findById(id1).orElseThrow(() -> new IllegalArgumentException("User with id " + id1 + " not found"));
        User user2 = userRepository.findById(id2).orElseThrow(() -> new IllegalArgumentException("User with id " + id2 + " not found"));
        if (user1.getConversations() == null) {
            return Optional.empty();
        }
        return user1.getConversations().stream()
                .filter(conversation -> conversation.getUsers().contains(user2))
                .findFirst();
    }


}
