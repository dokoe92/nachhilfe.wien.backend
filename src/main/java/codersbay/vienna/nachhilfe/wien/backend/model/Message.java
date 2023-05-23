package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

}
