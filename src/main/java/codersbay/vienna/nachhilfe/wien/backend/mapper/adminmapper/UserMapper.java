package codersbay.vienna.nachhilfe.wien.backend.mapper.adminmapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.admindto.UserDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackdto.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.feedbackmapper.FeedbackMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.ProfileMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final FeedbackMapper feedbackMapper;
    private final CoachingMapper coachingMapper;
    private final ProfileMapper profileMapper;

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO findUserDTO = new UserDTO();
        findUserDTO.setUserId(user.getId());
        if (user.getProfile() != null) {
            findUserDTO.setProfileId(user.getProfile().getId());
        }
        findUserDTO.setUserType(user.getUserType());
        findUserDTO.setFirstName(user.getFirstName());
        findUserDTO.setLastName(user.getLastName());
        findUserDTO.setBirthdate(user.getBirthdate());
        if (user.getProfile() != null) {
            findUserDTO.setProfile(profileMapper.toDTO(user.getProfile()));
        }

        if (user instanceof Student) {
            if (((Student) user).getFeedbacks() != null) {
                findUserDTO.setFeedbacks(((Student) user).getFeedbacks().stream().
                        map(feedbackMapper::toDTO)
                        .collect(Collectors.toCollection(TreeSet::new)));
            }
        }
        if (user instanceof Teacher) {
            if (((Teacher) user).getFeedbacks() != null) {
                findUserDTO.setFeedbacks(((Teacher) user).getFeedbacks().stream().
                        map(feedbackMapper::toDTO)
                        .collect(Collectors.toCollection(TreeSet::new)));
                findUserDTO.setDistricts(new HashSet<>(((Teacher) user).getDistricts()));
            }
        }
        if (user.getCoachings() != null) {
            findUserDTO.setCoachings(user.getCoachings().stream().map(coachingMapper::toDTO).collect(Collectors.toSet()));
        }


        return findUserDTO;
    }

}
