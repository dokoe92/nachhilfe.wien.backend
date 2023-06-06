package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Table(name="appointment")
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "start_coaching", nullable = false)
    private LocalDate start;

    @Setter
    @Column(name="end_coaching", nullable = false)
    private LocalDate end;

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

    @OneToOne
    @JoinColumn(name="fk_message_id")
    private Message message;

}
