package codersbay.vienna.nachhilfe.wien.backend.mapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserType(user.getUserType());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setFirstName(user.getLastName());

        return userDTO;
    }
}
