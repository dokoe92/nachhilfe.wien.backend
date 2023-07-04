package codersbay.vienna.nachhilfe.wien.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="profile")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Setter
    @Column(name = "picture", columnDefinition = "TEXT")
    private String imageBase64;

    @Setter
    @Column(name="description")
    private String description;

    @Setter
    @Column(name="active", nullable = false)
    private boolean active;

    @Setter
    @Column(name="average_rating")
    private Double averageRatingScore;

    /**
     * Represents the User associated with this profile.
     *
     * The @JsonBackReference annotation is used to manage the serialization and deserialization of this relationship from the non-owning side.
     */
    @OneToOne(mappedBy = "profile")
    @JsonBackReference(value="user-profile-reference")
    private User user;


}
