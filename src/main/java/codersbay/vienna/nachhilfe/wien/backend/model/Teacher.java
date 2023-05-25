package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@DiscriminatorValue("teacher")
@Getter
public class Teacher extends User {

    @Setter
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "teacher")
    private Set<Coaching> coachings;

}
