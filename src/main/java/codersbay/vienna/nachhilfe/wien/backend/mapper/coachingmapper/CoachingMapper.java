package codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoachingMapper {

    private final UserRepository userRepository;

    public CoachingDTO toDTO(Coaching coaching) {
        if (coaching == null) {
            return null;
        }

        CoachingDTO coachingDTO = new CoachingDTO();

        coachingDTO.setCoachingId(coaching.getId());
        coachingDTO.setSubject(coaching.getSubject());
        coachingDTO.setLevel(coaching.getLevel());
        coachingDTO.setRate(coaching.getRate());
        coachingDTO.setActive(coaching.isActive());
        coachingDTO.setTeacherId(coaching.getUser().getId());

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

        User user = userRepository.findById(coachingDTO.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        coaching.setUser(user);

        return coaching;
    }
}