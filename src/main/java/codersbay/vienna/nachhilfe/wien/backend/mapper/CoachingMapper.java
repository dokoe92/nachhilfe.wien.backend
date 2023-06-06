package codersbay.vienna.nachhilfe.wien.backend.mapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.District;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CoachingMapper {

    private final UserRepository userRepository;

    public CoachingDTO toDTO(Coaching coaching) {
        if (coaching == null) {
            return null;
        }

        CoachingDTO coachingDTO = new CoachingDTO();

        coachingDTO.setId(coaching.getId());
        coachingDTO.setSubject(coaching.getSubject());
        coachingDTO.setLevel(coaching.getLevel());
        coachingDTO.setRate(coaching.getRate());
        coachingDTO.setActive(coaching.isActive());
        coachingDTO.setUserId(coaching.getUser().getId());

        return coachingDTO;
    }

    public Coaching toEntity(CoachingDTO coachingDTO) {
        if (coachingDTO == null) {
            return null;
        }

        Coaching coaching = new Coaching();

        coaching.setSubject(coachingDTO.getSubject());
        coaching.setLevel(coachingDTO.getLevel());
        coaching.setRate(coachingDTO.getRate());
        coaching.setActive(coachingDTO.getActive());

        User user = userRepository.findById(coachingDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        coaching.setUser(user);

        return coaching;
    }
}