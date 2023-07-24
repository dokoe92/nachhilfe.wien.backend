package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Tag(name = "Message")
@SecurityRequirement(name="bearerAuth")
public class MessageController {

    private final MessageService messageService;
    private final JwtService jwtService;

    /**
     * Sends a message to a conversation.
     *
     * @param messageDTO      the MessageDTO object containing the message details
     * @param conversationId  the ID of the conversation
     * @return ResponseEntity with the sent MessageDTO object and HTTP status CREATED
     */
    @PostMapping("/send-message/{conversationId}")
    @Operation(
            description = "Add a message to a specific conversation"
    )
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO, @PathVariable Long conversationId, HttpServletRequest request) {
        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);

        MessageDTO message = messageService.sendMessage(messageDTO, conversationId, userId);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }



}
