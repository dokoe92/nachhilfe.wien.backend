package codersbay.vienna.nachhilfe.wien.backend.respository;

import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.searchobjects.TeacherSearchObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("select t " +
            "from Teacher t " +
            "inner join t.coachings c " +
            "inner join t.districts d " +
            "where (:#{#so.districtSet} = false or d in (:#{#so.districts})) " +
            "and (:#{#so.subjectSet} = false or c.subject = :#{#so.subject}) " +
            "and (:#{#so.minRateSet} = false or c.rate >= :#{#so.minRate}) " +
            "and (:#{#so.maxRateSet} = false or c.rate <= :#{#so.maxRate})" +
            "and (:#{#so.levelSet} = false or c.level = :#{#so.level})" +
            "and (:#{#so.ratingSet} = false or (select avg(f.rating) from Feedback f where f.teacher.id = t.id) >= :#{#so.rating})" +
            "and t.profile.active = true")
    List<Teacher> filterTeachers(@Param("so") TeacherSearchObject so);

    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.teacher.id = :teacherId")
    Double findAverageRating(Long teacherId);

    @Query("Select t from Teacher t inner join t.profile where t.profile.active = true")
    List<Teacher> findAllActive();


}
