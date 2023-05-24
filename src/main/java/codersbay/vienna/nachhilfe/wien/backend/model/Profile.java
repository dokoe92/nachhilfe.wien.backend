package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PROFILE")
public class Profile {

    @Id
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(name="username")
    private String username;

    @Getter
    @Setter
    @Column(name="password")
    private String password;

    @Getter
    @Setter
    @Column(name="email")
    private String email;

    @Getter
    @Setter
    @Column(name="image")
    private String imageBase64;

    @Getter
    @Setter
    @Column(name="description")
    private String description;

    @Getter
    @Setter
    @Column(name="active")
    private boolean active;

    @Getter
    @Setter
    @Column(name="average_rating")
    private Integer averageRatingScore;
}
