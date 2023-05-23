package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;

    @Getter
    @ManyToOne
    @JoinColumn(name="teacher")
    private Teacher teacher;

    @Getter
    @ManyToOne
    @JoinColumn(name="student")
    private Student student;

    @Getter
    @Setter
    String title;

    @Getter
    @Setter
    String content;

    @Getter
    @Setter
    LocalDate date;







}
