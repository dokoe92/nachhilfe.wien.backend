package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.model.Comment;
import codersbay.vienna.nachhilfe.wien.backend.service.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class Commentcontroller {

    private final CommentService commentService;


    public Commentcontroller(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    public void createComment(@RequestBody Comment comment) {
        commentService.createComment(comment);
    }
}
