package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }
}
