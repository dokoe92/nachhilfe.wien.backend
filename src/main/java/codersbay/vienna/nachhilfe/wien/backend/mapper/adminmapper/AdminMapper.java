package codersbay.vienna.nachhilfe.wien.backend.mapper.adminmapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.admindto.AdminDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.ProfileMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMapper {
    private final ProfileMapper profileMapper;

    public Admin toEntity(AdminDTO adminDTO){
        if (adminDTO == null){
            return null;
        }
        
        Admin admin = new Admin();
        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setBirthdate(adminDTO.getBirthdate());
        admin.setProfile(profileMapper.toEntity(adminDTO.getProfile()));
        
        return admin;
        
    }
}
