package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Getter
    @ManyToOne
    @JoinColumn(name="teacher", nullable = false)
    private Teacher teacher;

    @Getter
    @ManyToOne
    @JoinColumn(name="student", nullable = false)
    private Student student;

    int score;


}
