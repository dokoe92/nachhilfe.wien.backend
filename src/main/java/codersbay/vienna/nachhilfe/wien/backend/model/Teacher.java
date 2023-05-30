package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "teacher")
@Getter
public class Teacher extends User {

    @OneToMany(mappedBy = "teacher")
    private Set<Feedback> feedback = new HashSet<>();

}
