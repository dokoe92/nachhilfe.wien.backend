package codersbay.vienna.nachhilfe.wien.backend.mapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
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

    public User toEntity(CoachingsDTO coachingsDTO) {
        if (coachingsDTO == null) {
            return null;
        }

        User user = userRepository.findById(coachingsDTO.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
       Set<Coaching> coachings = user.getCoachings();
       coachings.addAll(coachingsDTO.getCoachings().stream()
               .map(coachingMapper::toEntity)
               .collect(Collectors.toSet()));

       user.setCoachings(coachings);

       return user;
    }
}