package ru.practicum.shareit.gateway.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ItemAnswer {

    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private UserDtoItem owner;
    private Long requestId;
    private BookingDtoItem lastBooking;
    private BookingDtoItem nextBooking;
    private List<CommentDtoItem> comments;

    @Data
    private static class UserDtoItem {
        private Long id;
        private String name;
        private String email;

    }

    @Data
    public static class BookingDtoItem {
        Long id;
        Long bookerId;
        Long itemId;

    }

    @Data
    private static class CommentDtoItem {
        private Long id;
        private String text;
        private String authorName;
        private Long itemId;
        private LocalDateTime created;

    }

}
