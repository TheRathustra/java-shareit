package ru.practicum.shareit.item.comment;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Long id;

    @NotEmpty
    private String text;
    private ItemDto item;
    private UserDto author;
    private LocalDateTime created;

    public static CommentDTO commentToDto(Comment comment) {
        CommentDTO c = new CommentDTO();
        c.setId(comment.getId());
        c.setText(comment.getText());
        if (comment.getItem() != null)
            c.setItem(ItemMapper.itemToDTO(comment.getItem()));
        if (comment.getAuthor() != null)
            c.setAuthor(UserMapper.userToDto(comment.getAuthor()));
        c.setCreated(comment.getCreated());
        return c;
    }

    public static Comment dtoToComment(CommentDTO comment) {
        Comment c = new Comment();
        c.setId(comment.getId());
        c.setText(comment.getText());
        if (comment.getItem() != null)
            c.setItem(ItemMapper.dtoToItem(comment.getItem()));
        if (comment.getAuthor() != null)
            c.setAuthor(UserMapper.dtoToUser(comment.getAuthor()));
        c.setCreated(comment.getCreated());
        return c;
    }

}
