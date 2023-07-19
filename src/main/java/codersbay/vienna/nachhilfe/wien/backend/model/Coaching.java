package codersbay.vienna.nachhilfe.wien.backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


@Entity
@Table(name = "coaching")
@Getter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Builder
@AllArgsConstructor
public class Coaching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "subject")
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Setter
    @Column(name = "level")
    private String level;

    @Setter
    @Column(name = "rate")
    private Double rate;

    @Setter
    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "coaching")
    @Setter
    @Builder.Default
    Set<Appointment> appointments = new TreeSet<>();

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    @Setter
    private User user;
}
