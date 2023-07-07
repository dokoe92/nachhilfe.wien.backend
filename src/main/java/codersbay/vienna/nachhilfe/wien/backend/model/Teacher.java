package codersbay.vienna.nachhilfe.wien.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "teacher")
@Getter
@SuperBuilder
public class Teacher extends User {

    public Teacher() {
        super(UserType.TEACHER);
    }

    @Getter
    @Setter
    @OneToMany(mappedBy = "teacher")
    @JsonManagedReference
    private Set<Feedback> feedbacks = new HashSet<>();

    @Getter
    @Setter
    @ElementCollection(targetClass = Districts.class)
    @CollectionTable(name = "districts",
            joinColumns = @JoinColumn(name = "teacher_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
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

    public void addFeedback(Feedback feedback) {
        if (feedback == null) {
            return;
        }
        this.getFeedbacks().add(feedback);
    }


}
