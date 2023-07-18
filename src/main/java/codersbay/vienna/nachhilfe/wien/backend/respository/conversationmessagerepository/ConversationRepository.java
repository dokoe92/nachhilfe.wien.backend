package codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository;

import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c JOIN FETCH c.users WHERE c.id = :conversationId")
    Optional<Conversation> findByIdWithUsers(@Param("conversationId") Long conversationId);
}
