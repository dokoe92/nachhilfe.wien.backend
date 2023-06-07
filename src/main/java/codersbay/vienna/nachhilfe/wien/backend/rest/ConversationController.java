package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.ConversationMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicateIdException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.MissingIdException;
import codersbay.vienna.nachhilfe.wien.backend.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conversation")
public class ConversationController {

    private final ConversationService conversationService;
    private final ConversationMapper conversationMapper;

    /**
     * Creates a conversation between two users - will be used to send messages.
     *
     * @param user1 the ID of the first user
     * @param user2 the ID of the second user
     * @return the ConversationDTO object representing the created conversation
     * @throws MissingIdException     if either user1 or user2 is missing
     * @throws DuplicateIdException   if user1 and user2 have the same ID
     */
    @PostMapping("/{user1}/{user2}")
    public ConversationDTO createConversation(@PathVariable Optional<Long> user1, @PathVariable Optional<Long> user2) {
        Set<Long> userIds = new HashSet<>();
        if (!user1.isPresent() || !user2.isPresent()) {
            throw new MissingIdException("Two Ids are required");
        }
        userIds.add(user1.get());
        boolean addUser2 = userIds.add(user2.get());

        if (!addUser2) {
            throw new DuplicateIdException("Users must have different IDs");
        }

        Conversation conversation = conversationService.createConversation(userIds);

        return conversationMapper.toDTO(conversation);
    }

    @GetMapping("/{conversationId}")
    public ConversationDTO findConversationById(@PathVariable Long conversationId) {
        return conversationService.findConversationById(conversationId);
    }

}
