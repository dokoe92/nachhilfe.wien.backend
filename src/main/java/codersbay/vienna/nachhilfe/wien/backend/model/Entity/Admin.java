package codersbay.vienna.nachhilfe.wien.backend.model.Entity;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;

@Getter
@DiscriminatorValue(value = "admin")
public class Admin extends User{

    public Admin() {
        super(UserType.ADMIN);
    }
}
