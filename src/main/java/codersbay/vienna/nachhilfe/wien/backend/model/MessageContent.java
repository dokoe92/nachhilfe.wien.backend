package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "MESSAGE_CONTENT")
public class MessageContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "PARENT_MESSAGE_CONTENT")
    private MessageContent parentMessageContent;

    @ManyToOne
    @JoinColumn(name = "FK_MESSAGE_ID")
    private Message message;
}
