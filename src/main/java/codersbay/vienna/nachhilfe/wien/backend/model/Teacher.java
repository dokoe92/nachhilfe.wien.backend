package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Teacher extends User {
    @Getter
    @Setter
    private String description;

    @Getter
    @OneToMany(mappedBy = "teacher")
    private List<Comment> comments;

    @Getter
    @OneToMany(mappedBy = "teacher")
    private List<Rating> rating;

    @ManyToMany
    private Set<Offer> offers;

    public Teacher() {};

    public Teacher(String firstName, String lastName, String email, String password, LocalDate birthdate, String imageBase64, String description) {
        super(firstName, lastName, email, password, birthdate, imageBase64);
        this.description = description;
    }
}
