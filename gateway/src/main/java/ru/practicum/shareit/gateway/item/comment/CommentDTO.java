package ru.practicum.shareit.gateway.item.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;

    @NotEmpty
    private String text;
    private ItemDtoComment item;
    private UserDtoComment author;
    private LocalDateTime created;

    @Data
    public static class UserDtoComment {
        private Long id;
        private String name;
        private String email;

    }

    @Data
    public static class ItemDtoComment {
        private Long id;
        private String name;
        private String description;

    }
}
