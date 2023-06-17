package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.feedbackDTO.FeedbackDTO;
import codersbay.vienna.nachhilfe.wien.backend.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
//@SecurityRequirement(name = "bearerAuth")
@Tag(name="Feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    @Operation(description = "Post a feedback to a teacher")
    public FeedbackDTO postFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        try {
            return feedbackService.sendFeedback(feedbackDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
