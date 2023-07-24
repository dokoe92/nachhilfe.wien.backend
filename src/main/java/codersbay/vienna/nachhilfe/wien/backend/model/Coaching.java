package codersbay.vienna.nachhilfe.wien.backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.TreeSet;


@Entity
@Table(name = "coaching")
@Getter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Builder
@AllArgsConstructor
public class Coaching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "subject")
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Setter
    @Column(name = "level")
    private String level;

    @Setter
    @Column(name = "rate")
    private Double rate;

    @Setter
    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "coaching")
    @Setter
    @Builder.Default
    Set<Appointment> appointments = new TreeSet<>();

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    @Setter
    private User user;
}
