package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("student")
@NoArgsConstructor
public class Student extends User {

    @Getter
    @OneToMany(mappedBy = "student")
    private Set<Feedback> feedback = new HashSet<>();

}
