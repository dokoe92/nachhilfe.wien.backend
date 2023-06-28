package codersbay.vienna.nachhilfe.wien.backend.respository;


import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoachingRepository extends JpaRepository<Coaching, Long> {
    boolean existsBySubjectAndUser(Subject subject, User user);
    List<Coaching> findByUser(User user);

}
