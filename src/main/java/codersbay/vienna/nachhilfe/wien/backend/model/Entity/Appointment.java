package codersbay.vienna.nachhilfe.wien.backend.model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue(value = "appointment")
@NoArgsConstructor
public class Appointment extends Message{

    @Setter
    @Column(name = "start_coaching", nullable = false)
    private LocalDateTime start;

    @Setter
    @Column(name="end_coaching", nullable = false)
    private LocalDateTime end;

    @Setter
    @Column(name="status")
    @Enumerated(value=EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name="fk_coaching_id", nullable = false)
    private Coaching coaching;

    @ManyToOne
    @JoinColumn(name="fk_student_id", nullable = false)
    private Student student;



}
