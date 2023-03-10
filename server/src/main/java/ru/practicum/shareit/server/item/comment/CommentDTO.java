package ru.practicum.shareit.server.item.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.user.model.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;

    private String text;
    private ItemDtoComment item;
    private UserDtoComment author;
    private LocalDateTime created;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.item = ItemDtoComment.itemToDTO(comment.getItem());
        this.author = UserDtoComment.userToDTO(comment.getAuthor());
        this.created = comment.getCreated();
    }

    public static Comment dtoToComment(CommentDTO comment) {
        if (comment == null)
            return null;

        Comment c = new Comment();
        c.setId(comment.getId());
        c.setText(comment.getText());
        c.setItem(ItemDtoComment.dtoToItem(comment.getItem()));
        c.setAuthor(UserDtoComment.dtoToUser(comment.getAuthor()));
        c.setCreated(comment.getCreated());
        return c;
    }

    @Data
    public static class UserDtoComment {
        private Long id;
        private String name;
        private String email;

        public static UserDtoComment userToDTO(User user) {
            if (user == null)
                return null;

            UserDtoComment dto = new UserDtoComment();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            return dto;
        }

        public static User dtoToUser(UserDtoComment dto) {
            if (dto == null)
                return null;

            User user = new User();
            user.setId(dto.getId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            return user;
        }
    }

    @Data
    public static class ItemDtoComment {
        private Long id;
        private String name;
        private String description;

        public static ItemDtoComment itemToDTO(Item item) {
            if (item == null)
                return null;

            ItemDtoComment dto = new ItemDtoComment();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setDescription(item.getDescription());
            return dto;
        }

        public static Item dtoToItem(ItemDtoComment dto) {
            if (dto == null)
                return null;

            Item item = new Item();
            item.setId(dto.getId());
            item.setName(dto.getName());
            item.setDescription(dto.getDescription());
            return item;
        }
    }
}
