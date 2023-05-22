package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Student extends User {
    public Student() {
        super();
    }
    public Student(String firstName, String lastName, String email, String password, LocalDate birthdate, String imageBase64) {
        super(firstName, lastName, email, password, birthdate, imageBase64);
    }

}
