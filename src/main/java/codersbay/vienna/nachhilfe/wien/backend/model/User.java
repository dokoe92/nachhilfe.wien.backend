package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "APPLICATION_USER")
public abstract class User {
    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name="first_name", length=255)
    private String firstName;

    @Getter
    @Setter
    @Column(name="last_name", length=255)
    private String lastName;

    @Getter
    @Setter
    @Column(name="birthdate")
    private LocalDate birthdate;

    @Getter
    @OneToOne
    private Profile profile;

    @OneToMany
    private Set<Coaching> coachings;

    @OneToMany(mappedBy = "sender")
    private Set<Message> sentMessages = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<Message> receivedMessages = new HashSet<>();

    public User() {};

}
