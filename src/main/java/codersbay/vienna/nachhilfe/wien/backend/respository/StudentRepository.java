package codersbay.vienna.nachhilfe.wien.backend.respository;

import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {

    private EntityManager entityManager;
    private StudentFindAllRepository studentFindAllRepository;

    public StudentRepository(EntityManager entityManager, StudentFindAllRepository studentFindAllRepository) {
        this.entityManager = entityManager;
        this.studentFindAllRepository = studentFindAllRepository;
    }

    public List<Student> findAll() {
        List<Student> studnetList = studentFindAllRepository.findAll();
    }


    public void createStudent(Student student) {
        entityManager.persist(student);
    }


}
