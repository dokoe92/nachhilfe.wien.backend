package codersbay.vienna.nachhilfe.wien.backend.model.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "student")
@Getter
public class Student extends User {

    public Student() {
        super(UserType.STUDENT);
    }

    @OneToMany(mappedBy = "student")
    private Set<Feedback> feedback = new HashSet<>();


}
