package codersbay.vienna.nachhilfe.wien.backend.model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="profile")
@Getter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name="user_name", nullable = false)
    private String userName;

    @Setter
    @Column(name="password", nullable = false)
    private String password;

    @Setter
    @Column(name="email", nullable = false)
    private String email;

    @Setter
    @Column(name="picture")
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
    @JsonBackReference
    private User user;


}