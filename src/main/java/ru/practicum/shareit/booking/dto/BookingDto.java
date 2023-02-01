package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.error.InvalidBookingException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long id;

    @FutureOrPresent
    private LocalDateTime start;

    @FutureOrPresent
    private LocalDateTime end;

    private Long itemId;

    private UserDtoBooking booker;

    private BookingStatus status;

    public static void validate(BookingDto dto) {
        if (dto.getStart().isAfter(dto.getEnd())) {
            throw new InvalidBookingException();
        }
    }

    public BookingDto(Booking booking) {
        this.id = booking.getId();
        this.start = booking.getStart();
        this.end = booking.getEnd();
        this.itemId = booking.getItem().getId();
        this.booker = BookingDto.UserDtoBooking.inctanceToDto(booking.getBooker());
        this.status = booking.getStatus();
    }

    public static Booking dtoToBooking(BookingDto dto) {
        if (dto == null)
            return null;

        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setStart(dto.getStart());
        booking.setEnd(dto.getEnd());
        booking.setStatus(dto.getStatus());
        booking.setBooker(UserDtoBooking.dtoToUser(dto.getBooker()));

        return booking;
    }

    @Data
    private static class UserDtoBooking {
        private Long id;
        private String name;
        private String email;

        public static User dtoToUser(UserDtoBooking dto) {
            if (dto == null)
                return null;

            User user = new User();
            user.setId(dto.getId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            return user;
        }

        public static UserDtoBooking inctanceToDto(User user) {
            if (user == null)
                return null;

            UserDtoBooking dto = new UserDtoBooking();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            return dto;
        }
    }

}
