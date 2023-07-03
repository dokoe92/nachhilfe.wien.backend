package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingDTO;
import codersbay.vienna.nachhilfe.wien.backend.dto.coachingdto.CoachingsDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingsMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.updaterequest.CoachingUpdateRequest;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicatedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachingService {

    private final CoachingRepository coachingRepository;
    private final UserRepository userRepository;
    private final CoachingsMapper coachingsMapper;
    private final CoachingMapper coachingMapper;
    private final TeacherRepository teacherRepository;
    private final JwtService jwtService;


    /**
     * Creates a list of coachings for a specific teacher.
     *
     * @param coachingsDTO object containing the coachings to be created
     * @param id           the ID of the teacher
     * @return the set of coachings with the created coachings
     * @throws ResourceNotFoundException if the teacher is not found
     * @throws DuplicatedException if an active subject is already saved for this user
     */
    @Transactional
    public Set<CoachingDTO> createCoachings(CoachingsDTO coachingsDTO, Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));


        Set<Coaching> coachings = coachingsDTO.getCoachings().stream()
                .map(coachingMapper::toEntity)
                .collect(Collectors.toSet());


        Set<CoachingDTO> savedCoachingDTO = new HashSet<>();
        Set<Subject> alreadyExistingSubjects = new HashSet<>();

        for (Coaching coaching : coachings) {
            if (coachingRepository.existsBySubjectAndUserAndActive(coaching.getSubject(), teacher, true)) {
                alreadyExistingSubjects.add(coaching.getSubject());
            }
            coaching.setActive(true);
            teacher.addCoachings(coaching);
            coaching = coachingRepository.save(coaching);
            savedCoachingDTO.add(coachingMapper.toDTO(coaching));
        }
        if (!alreadyExistingSubjects.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Subject subject : alreadyExistingSubjects) {
                sb.append(subject.name());
                sb.append(", ");
            }
            throw new DuplicatedException("Subjects : " + sb + " already exists for this teacher and is active!");
        }
        teacherRepository.save(teacher);
        return savedCoachingDTO;
    }

    /**
     *
     * @param request Parameters which should be changed (Level, Rate, Active)
     * @param coachingId
     * @return
     * If the request fields are null and active false then return the current coaching
     * If the active field is false then just set the coaching to inactive
     * if at least one field of the request is not null and active is true then set the old coaching to false and create a new one with true and updated request field
     */
    public CoachingDTO  updateCoaching(CoachingUpdateRequest request, Long coachingId, Long teacherId) {
        Coaching remainingCoaching = coachingRepository.findById(coachingId)
                .orElseThrow(() -> new ResourceNotFoundException("Coaching not found!"));

        if (!remainingCoaching.getUser().getId().equals(teacherId)) {
            throw new UserNotAuthorizedException("Teacher doesnt own this coaching!");
        }

        if (!request.getActive() && request.getLevel() == null && request.getRate() == null) {
            return coachingMapper.toDTO(remainingCoaching);
        }

        if (!request.getActive()) {
            remainingCoaching.setActive(request.getActive());
            coachingRepository.save(remainingCoaching);
            return coachingMapper.toDTO(remainingCoaching);
        }

        remainingCoaching.setActive(false);
        coachingRepository.save(remainingCoaching);

        Coaching activeCoaching = new Coaching();
        activeCoaching.setSubject(remainingCoaching.getSubject());
        activeCoaching.setUser(remainingCoaching.getUser());
        activeCoaching.setActive(true);

        if (request.getLevel() != null) {
            activeCoaching.setLevel(request.getLevel());
        } else {
            activeCoaching.setLevel(remainingCoaching.getLevel());
        }
        if (request.getRate() != null) {
            activeCoaching.setRate(request.getRate());
        } else {
            activeCoaching.setRate(remainingCoaching.getRate());
        }

        return coachingMapper.toDTO(activeCoaching);
    }


}
