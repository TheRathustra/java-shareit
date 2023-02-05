package ru.practicum.shareit.server.item.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentAnswerDto {
    private Long id;
    private String text;
    private String authorName;
    private Long itemId;
    private LocalDateTime created;

    public static CommentAnswerDto commentToDto(Comment comment) {
        CommentAnswerDto c = new CommentAnswerDto();
        c.setId(comment.getId());
        c.setText(comment.getText());
        if (comment.getItem() != null)
            c.setItemId(comment.getItem().getId());
        if (comment.getAuthor() != null)
            c.setAuthorName(comment.getAuthor().getName());
        c.setCreated(comment.getCreated());
        return c;
    }

}
