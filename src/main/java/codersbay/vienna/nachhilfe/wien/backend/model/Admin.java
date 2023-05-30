package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;

@Getter
@DiscriminatorValue(value = "admin")
public class Admin {
}
