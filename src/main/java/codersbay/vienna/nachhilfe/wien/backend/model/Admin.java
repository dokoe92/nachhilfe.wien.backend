package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue(value = "admin")
public class Admin extends User{

    public Admin() {
        super(UserType.ADMIN);
    }
}
