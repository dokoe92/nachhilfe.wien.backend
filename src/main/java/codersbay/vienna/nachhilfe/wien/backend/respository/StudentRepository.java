package codersbay.vienna.nachhilfe.wien.backend.respository;

import codersbay.vienna.nachhilfe.wien.backend.model.Comment;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Repository
public class StudentRepository {

    private final EntityManager entityManager;

    public StudentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void createStudent(Student student) {
        entityManager.persist(student);
    }




}
