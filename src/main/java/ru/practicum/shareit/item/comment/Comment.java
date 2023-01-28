package ru.practicum.shareit.item.comment;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments", schema = "public")
@NoArgsConstructor
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    private LocalDateTime created;
}