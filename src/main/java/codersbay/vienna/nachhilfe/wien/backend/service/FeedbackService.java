package codersbay.vienna.nachhilfe.wien.backend.service;


import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Feedback;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.FeedbackRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

/*
    public Feedback giveFeedbackToTeacher(Long studentId, Long teacherId) {
        User student = userRepository.findById(studentId).orElseThrow(()->new ResourceNotFoundException("Student not found!"));
        User teacher = userRepository.findById(teacherId).orElseThrow(()->new ResourceNotFoundException("Teacher not found!"));

        if (!(student instanceof Student) || !(teacher instanceof Teacher)) {

        }


    }
    */


}
