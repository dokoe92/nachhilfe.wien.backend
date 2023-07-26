package codersbay.vienna.nachhilfe.wien.backend.respository;

import codersbay.vienna.nachhilfe.wien.backend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByStudentIdAndCoachingId(Long studentId, Long coachingId);

    List<Appointment> findByStart(LocalDateTime startDate);

}
