package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "COACHING")
public class Coaching {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String level;

    @Getter
    @Setter
    private Double rate;

    @Getter
    @Setter
    private boolean active;
}
