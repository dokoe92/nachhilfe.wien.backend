package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("student")
@Getter
@NoArgsConstructor
public class Student extends User {

    @OneToMany(mappedBy = "student")
    private Set<Feedback> feedback = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Coaching> coachings = new HashSet<>();

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }
}
