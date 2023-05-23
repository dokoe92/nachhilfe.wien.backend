package codersbay.vienna.nachhilfe.wien.backend.respository;

import codersbay.vienna.nachhilfe.wien.backend.model.Comment;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

@Repository
public class CommentRepository {

    private final EntityManager entityManager;

    public CommentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void createComment(Comment comment) {
        entityManager.persist(comment);
    }
}
