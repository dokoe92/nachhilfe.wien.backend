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
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicatedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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
     * @param coachingsDTO object containing the coachings to be created
     * @param id           the ID of the teacher
     * @return the set of coachings with the created coachings
     * @throws ResourceNotFoundException if the teacher is not found
     * @throws DuplicatedException if the subject is already saved for this user
     */
    public CoachingsDTO createCoachings(CoachingsDTO coachingsDTO, Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Set<Coaching> coachings = coachingsDTO.getCoachings().stream()
                .map(coachingMapper::toEntity)
                .collect(Collectors.toSet());

        Set<CoachingDTO> savedCoachingDTO = new HashSet<>();

        for (Coaching coaching : coachings) {
            if (coachingRepository.existsBySubjectAndUser(coaching.getSubject(), teacher)) {
                throw new DuplicatedException("Subject" + coaching.getSubject() + " already exists for this teacher!");
            }
            coaching.setUser(teacher);
            coaching = coachingRepository.save(coaching); // save id and also update the coaching in the set
            teacher.addCoachings(coaching);
            savedCoachingDTO.add(coachingMapper.toDTO(coaching));
        }
        teacherRepository.save(teacher);

        CoachingsDTO coachingsDTOWithIds = new CoachingsDTO();
        coachingsDTOWithIds.setCoachings(savedCoachingDTO);

        return coachingsDTOWithIds;
    }
}
