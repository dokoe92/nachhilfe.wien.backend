package codersbay.vienna.nachhilfe.wien.backend.respository;


import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Subject;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachingRepository extends JpaRepository<Coaching, Long> {
    boolean existsBySubjectAndLevelAndRateAndUser(Subject subject, String level, Double rate, User user);
}
