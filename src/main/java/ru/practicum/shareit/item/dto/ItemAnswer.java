package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.comment.CommentAnswerDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Data
@NoArgsConstructor
public class ItemAnswer {

    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private UserDto owner;
    private ItemRequestDto request;
    private BookingDtoItem lastBooking;
    private BookingDtoItem nextBooking;
    private List<CommentAnswerDto> comments;

    public void setLastBooking(Booking lastBooking) {
        if (lastBooking == null)
            return;
        BookingDtoItem dto = new BookingDtoItem();
        dto.setId(lastBooking.getId());
        dto.setItemId(lastBooking.getItem().getId());
        dto.setBookerId(lastBooking.getBooker().getId());
        this.lastBooking = dto;
    }

    public void setNextBooking(Booking nextBooking) {
        if (nextBooking == null)
            return;
        BookingDtoItem dto = new BookingDtoItem();
        dto.setId(nextBooking.getId());
        dto.setItemId(nextBooking.getItem().getId());
        dto.setBookerId(nextBooking.getBooker().getId());
        this.nextBooking = dto;
    }

    @Data
    private class BookingDtoItem {
        Long id;
        Long bookerId;
        Long itemId;
    }
}
