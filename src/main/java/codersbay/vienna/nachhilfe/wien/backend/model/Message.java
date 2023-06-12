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
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Message {

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
    private User sender;

    @Setter
    @ManyToOne
    @JoinColumn(name="conversation_id")
    private Conversation conversations;

}
