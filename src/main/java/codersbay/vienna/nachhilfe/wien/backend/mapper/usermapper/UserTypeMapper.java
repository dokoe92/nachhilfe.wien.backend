package codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.UserTypeDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserTypeMapper {

    public UserTypeDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserTypeDTO userDTO = new UserTypeDTO();
        userDTO.setId(user.getId());
        userDTO.setUserType(user.getUserType());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());

        return userDTO;
    }
}
