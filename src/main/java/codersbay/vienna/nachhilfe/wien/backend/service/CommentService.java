package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Comment;
import codersbay.vienna.nachhilfe.wien.backend.respository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CommentService {

    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void createComment(Comment comment) {
        commentRepository.createComment(comment);
    }
}
