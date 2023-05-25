package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="profile")
@Getter
public class Profile {

    @Id
    private Long id;

    @Setter
    @Column(name="username")
    private String username;

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
