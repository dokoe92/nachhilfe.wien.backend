package codersbay.vienna.nachhilfe.wien.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Setter
    @OneToMany(mappedBy = "student")
    @JsonManagedReference(value="student-feedbacks-reference")
    private Set<Feedback> feedbacks = new HashSet<>();

    @Setter
    @OneToMany(mappedBy = "student")
    private Set<Appointment> appointments = new HashSet<>();

    public void addFeedback(Feedback feedback) {
        if (feedback == null) {
            return;
        }
        this.getFeedbacks().add(feedback);
    }



}
