package codersbay.vienna.nachhilfe.wien.backend.model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "teacher")
@Getter
public class Teacher extends User {

    public Teacher() {
        super(UserType.TEACHER);
    }

    @OneToMany(mappedBy = "teacher")
    private Set<Feedback> feedback = new HashSet<>();

    @Getter
    @Setter
    @ElementCollection(targetClass = District.class)
    @CollectionTable(name = "district",
            joinColumns = @JoinColumn(name = "coaching_id"))
    @Enumerated(EnumType.STRING)
    private Set<District> disctricts;


    /**
     * Establishes the bidirectional relationship between the teacher and the coaching.
     *
     * @param coaching the coaching to be added
     */
    public void addCoachings(Coaching coaching) {
        this.getCoachings().add(coaching);
        coaching.setUser(this);
    }

}
