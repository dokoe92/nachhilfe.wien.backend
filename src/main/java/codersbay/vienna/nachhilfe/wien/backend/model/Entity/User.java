package codersbay.vienna.nachhilfe.wien.backend.model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    /**
     * Sets the Profile associated with the user.
     * The @JsonManagedReference annotation is used to manage the serialization and deserialization of this relationship.
     */
    @Setter
    @OneToOne
    @JoinColumn(name="fk_profile_id", nullable = false)
    @JsonManagedReference
    private Profile profile;

    @Setter
    @OneToMany(mappedBy = "user")
    private Set<Coaching> coachings = new HashSet<>();


    /**
     * Many-to-Many relationship mapping with the Conversation entity.
     * Defines a join table "user_conversations" to represent the relationship between users and conversations.
     * The "user_id" column in the join table references the primary key of the User entity.
     * The "conversation_id" column in the join table references the primary key of the Conversation entity.
     * The @JsonIgnore annotation is used to exclude this property from serialization and deserialization.
     * The conversations property represents the set of conversations associated with the user.
     * **************************************************************************************************
     * IMPORTANT: This mapping signifies a many-to-many relationship between users and conversations.
     * The conversations property should be properly managed to maintain the integrity of the relationship.
     * **************************************************************************************************
     */
    @ManyToMany
    @JoinTable(
            name = "user_conversations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "conversation_id"))
    @JsonIgnore
    private Set<Conversation> conversations = new HashSet<>();


}
