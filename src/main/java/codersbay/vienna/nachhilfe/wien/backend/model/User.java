package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@MappedSuperclass
public  class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private LocalDate birthdate;
    @Getter
    @Setter
    private String imageBase64;

    public User() {};

    public User(String firstName, String lastName, String email, String password, LocalDate birthdate, String imageBase64) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.imageBase64 = imageBase64;
    }
}
