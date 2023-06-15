package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found!"));
    }


}
