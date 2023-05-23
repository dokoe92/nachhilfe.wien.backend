package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Getter
    @Setter
    String subject;

    @Getter
    @Setter
    String level;

    @ManyToMany
    Set<Teacher> teachers;


}
