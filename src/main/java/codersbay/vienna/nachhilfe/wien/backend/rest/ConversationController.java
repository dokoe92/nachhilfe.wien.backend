package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.messagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.ConversationMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicateIdException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.MissingIdException;
import codersbay.vienna.nachhilfe.wien.backend.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conversation")
public class ConversationController {

    private final ConversationService conversationService;
    private final ConversationMapper conversationMapper;

    @PostMapping("/{user1}/{user2}")
    private ConversationDTO createConversation(@PathVariable Optional<Long> user1, @PathVariable Optional<Long> user2) {
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
}
