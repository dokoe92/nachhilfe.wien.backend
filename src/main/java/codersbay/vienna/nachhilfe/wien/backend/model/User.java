package codersbay.vienna.nachhilfe.wien.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@Table(name = "application_user")
@Getter
public abstract class User {

    public User(UserType userType) {
        this.userType = userType;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Transient
    private UserType userType;

    @Setter
    @Column(name="first_name", nullable = false)
    private String firstName;

    @Setter
    @Column(name="last_name", nullable = false)
    private String lastName;

    @Setter
    @Column(name="birthdate", nullable = false)
    private LocalDate birthdate;

    @Setter
    @Column(name="description")
    private String description;

    @Setter
    @OneToOne
    @JoinColumn(name="fk_profile_id", nullable = false)
    @JsonManagedReference
    private Profile profile;


    @ManyToMany
    @JoinTable(
            name = "user_conversations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "conversation_id"))
    private Set<Conversation> conversations = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Coaching> coachings = new HashSet<>();



}
