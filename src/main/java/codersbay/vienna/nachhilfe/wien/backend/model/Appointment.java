package codersbay.vienna.nachhilfe.wien.backend.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private LocalDateTime start;

    @Setter
    @Column(name = "end_coaching")
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

    @Setter
    @Column(name="confirmed")
    private Boolean confirmed;



}
