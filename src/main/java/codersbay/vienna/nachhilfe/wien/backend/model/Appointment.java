package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Appointment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_coaching")
    private LocalDate start;

    @Column(name="end_coaching")
    private LocalDate end;

    @ManyToOne
    @JoinColumn(name="fk_coaching_id")
    private Coaching coaching;

    @ManyToOne
    @JoinColumn(name="fk_message_id")
    private Message message;

}
