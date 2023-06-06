package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.CoachingsMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicateCoachingException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CoachingService {

    private final CoachingRepository coachingRepository;
    private final UserRepository userRepository;
    private final CoachingsMapper coachingsMapper;
    private final CoachingMapper coachingMapper;
    private final TeacherRepository teacherRepository;

    public CoachingsDTO createCoachings(CoachingsDTO coachingsDTO, Long id) {
        CoachingsDTO responseDTO = new CoachingsDTO();
        Set<CoachingDTO> responseCoachings = responseDTO.getCoachings();

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        responseDTO.setUserId(id);

        for (CoachingDTO coachingDTO : coachingsDTO.getCoachings()) {
            coachingDTO.setUserId(id);
        }

        Set<Coaching> coachings = coachingsDTO.getCoachings().stream()
                .map(coachingMapper::toEntity)
                .collect(Collectors.toSet());


        for (Coaching coaching : coachings) {
            coaching = coachingRepository.save(coaching);
            CoachingDTO coachingDTO = coachingMapper.toDTO(coaching);
            responseCoachings.add(coachingDTO);
            teacher.addCoachings(coaching);
        }
        responseDTO.setCoachings(responseCoachings);

        teacherRepository.save(teacher);

        return responseDTO;
    }
}
