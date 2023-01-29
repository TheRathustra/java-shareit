package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingAnswer {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemDtoBooking item;
    private UserDtoBooking booker;
    private String status;

    public BookingAnswer(Booking booking) {
        this.id = booking.getId();
        this.start = booking.getStart();
        this.end = booking.getEnd();
        this.status = booking.getStatus().name();
        this.booker = UserDtoBooking.userToDTO(booking.getBooker());
        this.item = ItemDtoBooking.iteToDTO(booking.getItem());
    }

    public static BookingAnswer bookingAnswer(Booking booking) {
        if (booking == null)
            return null;

        return new BookingAnswer(booking);
    }

    @Data
    public static class UserDtoBooking {
        private Long id;
        private String name;
        private String email;

        public static UserDtoBooking userToDTO(User user) {
            if (user == null)
                return null;

            UserDtoBooking dto = new UserDtoBooking();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            return dto;
        }

    }

    @Data
    public static class ItemDtoBooking {
        private Long id;
        private String name;
        private String description;
        private Boolean available;
        private UserDtoBooking owner;

        public static ItemDtoBooking iteToDTO(Item item) {
            if (item == null)
                return null;

            ItemDtoBooking dto = new ItemDtoBooking();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setDescription(item.getDescription());
            dto.setAvailable(item.getAvailable());
            dto.setOwner(UserDtoBooking.userToDTO(item.getOwner()));
            return dto;
        }

    }

}
