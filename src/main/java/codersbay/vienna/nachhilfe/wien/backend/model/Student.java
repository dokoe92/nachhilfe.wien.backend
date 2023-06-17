package codersbay.vienna.nachhilfe.wien.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "student")
@Getter
public class Student extends User {

    public Student() {
        super(UserType.STUDENT);
    }

    @Getter
    @Setter
    @OneToMany(mappedBy = "student")
    @JsonBackReference
    private Set<Feedback> feedbacks = new HashSet<>();

    public void addFeedback(Feedback feedback) {
        if (feedback == null) {
            return;
        }
        this.getFeedbacks().add(feedback);
    }


}
