package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Student extends User {

    @Getter
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Getter
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Rating> rating;

    public Student() {};
    public Student(String firstName, String lastName, String email, String password, LocalDate birthdate, String imageBase64) {
        super(firstName, lastName, email, password, birthdate, imageBase64);
    }

}
