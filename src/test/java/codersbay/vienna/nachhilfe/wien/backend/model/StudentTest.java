package codersbay.vienna.nachhilfe.wien.backend.model;

import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class StudentTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void createStudentTest() {
        studentRepository.createStudent(new Student("Dominik","Köberl", "dominik@köberl.at", "1234", LocalDate.now(), "abcdef"));
    }
}
