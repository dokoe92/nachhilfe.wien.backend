package codersbay.vienna.nachhilfe.wien.backend.respository;


import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachingRepository extends JpaRepository<Coaching, Long> {
    boolean existsBySubjectAndLevelAndRateAndUser(Subject subject, String level, Double rate, User user);
}
