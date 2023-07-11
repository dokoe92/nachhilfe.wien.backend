package codersbay.vienna.nachhilfe.wien.backend.respository;

import codersbay.vienna.nachhilfe.wien.backend.model.Appointment;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByStudentIdAndCoachingId(Long studentId, Long coachingId);

}
