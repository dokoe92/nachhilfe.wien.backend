package codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository;

import codersbay.vienna.nachhilfe.wien.backend.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c JOIN FETCH c.users WHERE c.id = :conversationId")
    Optional<Conversation> findByIdWithUsers(@Param("conversationId") Long conversationId);

    @Query(value = """
    SELECT c.*
    FROM conversation c
    INNER JOIN user_conversations uc ON c.id = uc.conversation_id
    LEFT JOIN (
        SELECT conversation_id, MAX(timestamp) as max_timestamp
        FROM message
        GROUP BY conversation_id
    ) m ON c.id = m.conversation_id
    WHERE uc.user_id = :userId
    ORDER BY m.max_timestamp DESC
""", nativeQuery = true)
    List<Conversation> findUserConversationsOrderByLatestMessage(@Param("userId") Long userId);


}
