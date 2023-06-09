package codersbay.vienna.nachhilfe.wien.backend.respository;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmailAndPassword(String email, String Password);
    Optional<Profile> findByEmail(String email);
}
