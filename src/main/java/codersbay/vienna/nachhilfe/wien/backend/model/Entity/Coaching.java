package codersbay.vienna.nachhilfe.wien.backend.model.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "coaching")
@Getter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Coaching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "subject")
    @Enumerated(EnumType.STRING)
    private Subject subject;

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
    @JoinColumn(name="fk_user_id")
    @Setter
    private User user;


}
