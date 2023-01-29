package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.error.InvalidBookingException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Data
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

        private static User dtoToUser(UserDtoBooking dto) {
            if (dto == null)
                return null;

            User user = new User();
            user.setId(dto.getId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            return user;
        }
    }

}
