package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO, @PathVariable Long conversationId) {
        MessageDTO message = messageService.sendMessage(messageDTO, conversationId);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("sendAppointment/{conversationId}/{coachingId}")
    public ResponseEntity<AppointmentDTO> sendAppointment(@RequestBody AppointmentDTO appointmentDTO, @PathVariable Long conversationId, @PathVariable Long coachingId) {
        AppointmentDTO appointment =  messageService.sendAppointment(appointmentDTO, conversationId, coachingId);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

}
