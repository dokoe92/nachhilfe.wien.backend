package codersbay.vienna.nachhilfe.wien.backend.model;

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
public class Coaching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "subject")
    @Enumerated(EnumType.STRING)
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
    @JoinColumn(name="fk_user_id")
    private User user;

    @ElementCollection(targetClass = District.class)
    @JoinColumn(name = "collection_districts")
    @Enumerated(EnumType.STRING)
    private Set<District> disctricts;


}
