package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue(value = "appointment")
public class Appointment extends Message{

    public Appointment() {
        super(MessageType.APPOINTMENT);
    }

    @Setter
    @Column(name = "start_coaching")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime start;

    @Setter
    @Column(name="end_coaching")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime end;

    @Setter
    @Column(name="status")
    @Enumerated(value=EnumType.STRING)
    private Status status;

    @Setter
    @ManyToOne
    @JoinColumn(name="fk_coaching_id")
    private Coaching coaching;

    @Setter
    @ManyToOne
    @JoinColumn(name="fk_student_id")
    private Student student;



}
