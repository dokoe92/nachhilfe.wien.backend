package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("teacher")
@NoArgsConstructor
public class Teacher extends User {
    @Getter
    @Setter
    private String description;



}
