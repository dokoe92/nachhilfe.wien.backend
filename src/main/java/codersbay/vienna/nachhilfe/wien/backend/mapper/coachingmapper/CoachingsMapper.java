package codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CoachingsMapper {

    private final UserRepository userRepository;
    private final CoachingMapper coachingMapper;
    public CoachingsDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        CoachingsDTO coachingsDTO = new CoachingsDTO();
        coachingsDTO.setUserId(user.getId());
        coachingsDTO.setCoachings(user.getCoachings().stream()
                .map(coachingMapper::toDTO)
                .collect(Collectors.toSet()));

        return coachingsDTO;
    }


}