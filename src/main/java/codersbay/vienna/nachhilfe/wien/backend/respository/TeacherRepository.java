package codersbay.vienna.nachhilfe.wien.backend.respository;

import codersbay.vienna.nachhilfe.wien.backend.model.Districts;
import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    @Query("select t " +
            "from Teacher t " +
            "inner join t.coachings c " +
            //"where t.districts in (:districts) " +
            "where (:subject is null or c.subject = :subject) " +
            "and (:minRate is null or c.rate >= :minRate) " +
            "and (:maxRate is null or c.rate <= :maxRate)")
    List<Teacher> filterTeachers (
                           //@Param("districts") List<Districts> districts,
                           @Param("subject") Subject subject,
                           @Param("minRate") long min,
                           @Param("maxRate") long max);
}
