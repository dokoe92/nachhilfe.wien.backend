package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Teacher extends User {
    String description;

    public Teacher() {};

    public Teacher(String firstName, String lastName, String email, String password, LocalDate birthdate, String imageBase64, String description) {
        super(firstName, lastName, email, password, birthdate, imageBase64);
        this.description = description;
    }
}
