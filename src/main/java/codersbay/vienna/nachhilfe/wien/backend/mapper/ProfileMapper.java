package codersbay.vienna.nachhilfe.wien.backend.mapper;

import codersbay.vienna.nachhilfe.wien.backend.DTO.ProfileDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public Profile toEntity(ProfileDTO profileDTO) {
        if (profileDTO == null) {
            return null;
        }

        Profile profile = new Profile();
        profile.setUserName(profileDTO.getUserName());
        profile.setPassword(profileDTO.getPassword());
        profile.setEmail(profileDTO.getEmail());
        profile.setDescription(profileDTO.getDescription());
        profile.setActive(profileDTO.getActive());
        profile.setAverageRatingScore(profileDTO.getAverageRatingScore());

        return profile;
    }

    public ProfileDTO toDTO(Profile profile) {
        if (profile == null) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUserName(profile.getUserName());
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setDescription(profile.getDescription());
        profileDTO.setActive(profile.isActive());
        profileDTO.setAverageRatingScore(profile.getAverageRatingScore());

        return profileDTO;
    }

}