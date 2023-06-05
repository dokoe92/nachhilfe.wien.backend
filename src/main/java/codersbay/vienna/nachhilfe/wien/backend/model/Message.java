package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="message")
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime timestamp;

    @Setter
    @Column(name = "title")
    private String title;

    @Setter
    @Column(name="content")
    private String content;

    @ManyToOne
    private User sender;

    @ManyToOne
    @JoinColumn(name="conversation_id")
    private Conversation conversations;

}
