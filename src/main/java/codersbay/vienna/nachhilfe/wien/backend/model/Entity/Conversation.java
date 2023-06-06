package codersbay.vienna.nachhilfe.wien.backend.model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Table(name="conversation")
@Getter
@NoArgsConstructor
@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter
    @ManyToMany(mappedBy = "conversations")
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    @Setter
    @OneToMany
    Set<Message> messages = new HashSet<>();
}
