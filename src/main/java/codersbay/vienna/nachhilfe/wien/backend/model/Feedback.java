package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Feedback {
    @Getter
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Getter
    @ManyToOne
    @JoinColumn(name="fk_teacher_id")
    private Teacher teacher;

    @Getter
    @ManyToOne
    @JoinColumn(name="fk_student_id")
    private Student student;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private Integer rating;

    @Getter
    @Setter
    private LocalDate date;







}
