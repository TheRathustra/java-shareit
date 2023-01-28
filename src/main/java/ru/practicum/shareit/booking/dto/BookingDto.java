package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.error.InvalidBookingException;
import ru.practicum.shareit.user.dto.UserDto;

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

    private UserDto booker;

    private BookingStatus status;

    public static void validate(BookingDto dto) {
        if (dto.getStart().isAfter(dto.getEnd())) {
            throw new InvalidBookingException();
        }

    }

}
