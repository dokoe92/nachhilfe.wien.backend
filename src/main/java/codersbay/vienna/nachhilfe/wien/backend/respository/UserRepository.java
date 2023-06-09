package codersbay.vienna.nachhilfe.wien.backend.respository;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



}
