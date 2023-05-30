package codersbay.vienna.nachhilfe.wien.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "message_content")
@Getter
@NoArgsConstructor
public class MessageContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="content", length = 4000)
    private String content;

    @Column(name="date")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "fk_parent_message_content")
    private MessageContent parentMessageContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_message_id")
    private Message message;
}
