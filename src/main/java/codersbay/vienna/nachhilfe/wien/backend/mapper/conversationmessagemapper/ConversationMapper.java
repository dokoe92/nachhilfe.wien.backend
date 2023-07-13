package codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.MessageDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserTypeDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.UserTypeMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Appointment;
import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import codersbay.vienna.nachhilfe.wien.backend.model.Message;
import codersbay.vienna.nachhilfe.wien.backend.model.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ConversationMapper {

    private final MessageMapper messageMapper;
    private final AppointmentMapper appointmentMapper;
    private final UserTypeMapper userTypeMapper;


    public ConversationDTO toDTO(Conversation conversation) {
       /* Set<MessageDTO> messageDTOS = conversation.getMessages().stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toSet());*/

        Set <Message> messages = conversation.getMessages();
        Set<MessageDTO> messageDTOS = new HashSet<>();
        if (messages != null) {
            for (Message message : messages) {
                if (message.getMessageType() == MessageType.APPOINTMENT) {
                    AppointmentDTO appointmentDTO = appointmentMapper.toDTO((Appointment) message);
                    messageDTOS.add(appointmentDTO);
                } else {
                    MessageDTO messageDTO = messageMapper.toDTO(message);
                    messageDTOS.add(messageDTO);
                }
            }
        }


        Set<UserTypeDTO> userTypeDTOS = conversation.getUsers().stream()
                .map(userTypeMapper::toDTO)
                .collect(Collectors.toSet());
        return new ConversationDTO(conversation.getId(),  userTypeDTOS, messageDTOS );
    }
}
