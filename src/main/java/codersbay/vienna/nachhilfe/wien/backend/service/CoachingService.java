package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.CoachingRequest;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.DuplicateCoachingException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CoachingService {

    private final CoachingRepository coachingRepository;
    private final UserRepository userRepository;


    public Coaching createCoaching(CoachingRequest coachingRequest, Long id) {
        Coaching coaching = new Coaching();
        coaching.setSubject(coachingRequest.getSubject());
        coaching.setLevel(coachingRequest.getLevel());
        coaching.setRate(coachingRequest.getRate());
        coaching.setActive(true);

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        } else {
            coaching.setUser(user.get());
        }

        if (coachingRepository.existsBySubjectAndLevelAndRateAndUser(
                coaching.getSubject(), coaching.getLevel(), coaching.getRate(), coaching.getUser())) {
            throw new DuplicateCoachingException("Coaching with subject, level, rate and user already found");
        }
        return coachingRepository.save(coaching);
    }
}
