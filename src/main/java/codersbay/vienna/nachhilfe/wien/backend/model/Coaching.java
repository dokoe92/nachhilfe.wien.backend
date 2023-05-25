package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "COACHING")
@Getter
public class Coaching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "subject")
    private String subject;

    @Setter
    @Column(name="level")
    private String level;

    @Setter
    @Column(name = "rate")
    private Double rate;

    @Setter
    @Column(name="active")
    private boolean active;

    @OneToMany(mappedBy = "coaching")
    Set<Appointment> appointments = new HashSet<>();

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_teacher_id")
    private Student student;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="fk_student_id")
    private Teacher teacher;

    @ElementCollection(targetClass = District.class)
    @JoinColumn(name = "collection_districts")
    @Enumerated(EnumType.STRING)
    private Set<District> disctricts;


}
