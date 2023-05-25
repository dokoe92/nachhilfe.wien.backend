package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
public class Feedback {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="fk_teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name="fk_student_id")
    private Student student;

    @Setter
    @Column(name = "content")
    private String content;

    @Setter
    @Column(name="rating")
    private Integer rating;

    @Setter
    @Column(name = "date")
    private LocalDate date;


}
