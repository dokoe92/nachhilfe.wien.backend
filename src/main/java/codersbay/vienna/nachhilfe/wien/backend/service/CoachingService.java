package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingsMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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


    /**
     * Creates a list of coachings for a specific teacher.
     *
     * @param coachingsDTO the CoachingsDTO object containing the coachings to be created
     * @param id           the ID of the teacher
     * @return the CoachingsDTO object with the created coachings including their ids from the database
     * @throws ResourceNotFoundException if the teacher is not found
     */
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
