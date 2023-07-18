package codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.ProfileDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    private final TeacherRepository teacherRepository;

    public Profile toEntity(ProfileDTO profileDTO) {
        if (profileDTO == null) {
            return null;
        }

        Profile profile = new Profile();
        profile.setPassword(profileDTO.getPassword());
        profile.setEmail(profileDTO.getEmail());
        profile.setDescription(profileDTO.getDescription());
        profile.setImageBase64(profileDTO.getImageBase64());
        profile.setActive(profileDTO.getActive());

        return profile;
    }

    public ProfileDTO toDTO(Profile profile) {
        if (profile == null) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setDescription(profile.getDescription());
        profileDTO.setActive(profile.isActive());
        profileDTO.setImageBase64(profile.getImageBase64());
        if (profile.getUser() instanceof Teacher) {
            profileDTO.setAverageRatingScore(teacherRepository.findAverageRating(profile.getUser().getId()));
        }

        return profileDTO;
    }

}
