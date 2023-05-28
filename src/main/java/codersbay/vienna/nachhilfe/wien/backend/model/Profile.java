package codersbay.vienna.nachhilfe.wien.backend.model;

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
    @Column(name="user_name")
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

    public Profile(String userName, String password, String email, String description) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.description = description;
    }
}
