package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.ConversationMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicatedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.MissingIdException;
import codersbay.vienna.nachhilfe.wien.backend.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conversation")
@Tag(name = "Conversation")
@SecurityRequirement(name="bearerAuth")
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
     * @throws DuplicatedException   if user1 and user2 have the same ID
     */
    @PostMapping("/create-conversation/{user1}/{user2}")
    @Operation(
            description = "Create a conversation between two users where messages can be added"
    )
    public ResponseEntity<ConversationDTO> createConversation(@PathVariable Long user1, @PathVariable Long user2) {
        if (user1 == null || user2 == null) {
            throw new MissingIdException("Both user IDs are required");
        }

        if (Objects.equals(user1, user2)) {
            throw new DuplicatedException("Users must have different IDs");
        }

        Optional<Conversation> existingConversation = conversationService.findConversationOfUsers(user1, user2);
        if (existingConversation.isPresent()) {
            return new ResponseEntity<>(conversationMapper.toDTO(existingConversation.get()), HttpStatus.OK);
        }

        Set<Long> userIds = new HashSet<>(Arrays.asList(user1, user2));
        Conversation conversation = conversationService.createConversation(userIds);
        ConversationDTO conversationDTO = conversationMapper.toDTO(conversation);
        return new ResponseEntity<>(conversationDTO, HttpStatus.CREATED);
    }


    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationDTO> findConversationById(@PathVariable Long conversationId) {
        ConversationDTO conversationDTO = conversationService.findConversationById(conversationId);
        return new ResponseEntity<>(conversationDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserConversationDTO> findConversationsOfUser (@PathVariable Long userId) {
        UserConversationDTO userConversationDTO = conversationService.findConversationsOfUser(userId);
        return new ResponseEntity<>(userConversationDTO, HttpStatus.OK);
    }

}
