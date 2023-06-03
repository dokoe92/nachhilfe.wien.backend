package codersbay.vienna.nachhilfe.wien.backend.respository.message;

import codersbay.vienna.nachhilfe.wien.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
