package codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository;

import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
