package codersbay.vienna.nachhilfe.wien.backend.mapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConversationMapper {

    private final UserMapper userMapper;
    private final MessageMapper messageMapper;
    public UserConversationDTO toDTO(User user) {


    }


}


