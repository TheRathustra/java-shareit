package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public ItemAnswer(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.available = item.getAvailable();
        this.owner = UserDtoItem.userToDTO(item.getOwner());
        this.requestId = item.getRequestId();
        this.comments = new ArrayList<>();
    }

    public void setComments(List<Comment> comments) {
        if (comments.isEmpty())
            return;

        this.comments = comments.stream().map(CommentDtoItem::commentToDTO)
                .collect(Collectors.toList());
    }

    public void setLastBooking(Booking lastBooking) {
        this.lastBooking = BookingDtoItem.bookingToDTO(lastBooking);
    }

    public void setNextBooking(Booking nextBooking) {
        this.nextBooking = BookingDtoItem.bookingToDTO(nextBooking);
    }

    public static ItemAnswer itemToAnswerDTO(Item item) {
        if (item == null)
            return null;

        return new ItemAnswer(item);
    }

    @Data
    private static class UserDtoItem {
        private Long id;
        private String name;
        private String email;

        public static UserDtoItem userToDTO(User user) {
            if (user == null)
                return null;

            UserDtoItem dto = new UserDtoItem();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            return dto;
        }
    }

    @Data
    private static class BookingDtoItem {
        Long id;
        Long bookerId;
        Long itemId;

        public static BookingDtoItem bookingToDTO(Booking booking) {
            if (booking == null)
                return null;

            BookingDtoItem dto = new BookingDtoItem();
            dto.setId(booking.getId());
            if (booking.getBooker() != null)
                dto.setBookerId(booking.getBooker().getId());
            if (booking.getItem() != null)
                dto.setItemId(booking.getItem().getId());

            return dto;
        }

    }

    @Data
    private static class CommentDtoItem {
        private Long id;
        private String text;
        private String authorName;
        private Long itemId;
        private LocalDateTime created;

        public static CommentDtoItem commentToDTO(Comment comment) {
            if (comment == null)
                return null;

            CommentDtoItem dto = new CommentDtoItem();
            dto.setId(comment.getId());
            dto.setText(comment.getText());
            if (comment.getItem() != null)
                dto.setAuthorName(comment.getAuthor().getName());
            if (comment.getItem() != null)
                dto.setItemId(comment.getItem().getId());

            dto.setCreated(comment.getCreated());

            return dto;
        }

    }

}
