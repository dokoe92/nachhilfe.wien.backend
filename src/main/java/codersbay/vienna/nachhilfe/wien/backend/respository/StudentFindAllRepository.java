package codersbay.vienna.nachhilfe.wien.backend.respository;

import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import org.springframework.data.repository.CrudRepository;


public interface StudentFindAllRepository extends CrudRepository<Student, Integer> {
}
