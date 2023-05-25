package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="profile")
@Getter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name="username")
    private String userName;

    @Setter
    @Column(name="password")
    private String password;

    @Setter
    @Column(name="email")
    private String email;

    @Setter
    @Column(name="picture")
    private String imageBase64;

    @Setter
    @Column(name="description")
    private String description;

    @Setter
    @Column(name="active")
    private boolean active;

    @Setter
    @Column(name="average_rating")
    private Integer averageRatingScore;
}
