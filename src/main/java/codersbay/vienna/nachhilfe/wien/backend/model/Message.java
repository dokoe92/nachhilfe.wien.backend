package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="message")
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="message_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "message")
public class Message implements Comparable<Message>{

    public Message () {
        this.messageType = MessageType.MESSAGE;
    }

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    @Enumerated(EnumType.STRING)
    @Transient
    private MessageType messageType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    private LocalDateTime timestamp;

    @Setter
    @Column(name = "title")
    private String title;

    @Setter
    @Column(name="content")
    private String content;

    @Setter
    @ManyToOne
    @JoinColumn(name="sender_id")
    private User sender;

    @Setter
    @ManyToOne
    @JoinColumn(name="conversation_id")
    private Conversation conversation;


    @Override
    public int compareTo(Message o) {
        return 0;
    }
}
