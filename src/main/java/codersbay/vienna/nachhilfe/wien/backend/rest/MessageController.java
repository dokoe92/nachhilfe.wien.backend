package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /**
     * Sends a message to a conversation.
     *
     * @param messageDTO      the MessageDTO object containing the message details
     * @param conversationId  the ID of the conversation
     * @return ResponseEntity with the sent MessageDTO object and HTTP status CREATED
     */
    @PostMapping("/sendMessage/{conversationId}")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO, @PathVariable Long conversationId) {
        MessageDTO message = messageService.sendMessage(messageDTO, conversationId);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
