package codersbay.vienna.nachhilfe.wien.backend.model;

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
    @ElementCollection(targetClass = Districts.class)
    @CollectionTable(name = "districts",
            joinColumns = @JoinColumn(name = "teacher_id"))
    @Enumerated(EnumType.STRING)
    private Set<Districts> districts = new HashSet<>();


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
