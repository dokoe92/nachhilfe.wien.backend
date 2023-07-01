package codersbay.vienna.nachhilfe.wien.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name="feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="fk_teacher_id")
    @Setter
    @JsonBackReference(value="teacher-feedbacks-reference")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name="fk_student_id")
    @Setter
    @JsonBackReference(value="student-feedbacks-reference")
    private Student student;

    @Setter
    @Column(name = "content", length=5000)
    private String content;

    @Setter
    @Column(name= "title")
    private String title;

    @Setter
    @Column(name="rating")
    private Integer rating;

    @Setter
    @Column(name = "date")
    private LocalDate date;


}
