package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@DiscriminatorValue(value = "appointment")
public class Appointment extends Message{

    public Appointment() {
        super(MessageType.APPOINTMENT);
    }

    @Setter
    @Column(name = "start_coaching")
    private ZonedDateTime start;

    @Setter
    @Column(name = "end_coaching")
    private ZonedDateTime end;

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

    @Override
    public int compareTo(Message other) {
        if (other instanceof Appointment otherAppointment) {
            return this.getStart().compareTo(otherAppointment.getStart());
        } else {
            // If the other object is not an AppointmentDTO, consider it "greater" for consistent ordering
            return 1;
        }
    }

}
