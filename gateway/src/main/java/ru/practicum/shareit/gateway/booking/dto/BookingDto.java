package ru.practicum.shareit.gateway.booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.gateway.booking.error.InvalidBookingException;
import ru.practicum.shareit.gateway.booking.model.BookingStatus;

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

    @Data
    private static class UserDtoBooking {
        private Long id;
        private String name;
        private String email;

    }

}
