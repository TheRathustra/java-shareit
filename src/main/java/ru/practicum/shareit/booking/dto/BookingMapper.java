package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.user.dto.UserMapper;

public class BookingMapper {
    public static Booking dtoToBooking(BookingDto dto) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setStart(dto.getStart());
        booking.setEnd(dto.getEnd());
        booking.setStatus(dto.getStatus());

        if (dto.getBooker() != null)
            booking.setBooker(UserMapper.dtoToUser(dto.getBooker()));

        return booking;
    }

    public static BookingAnswer bookingAnswer(Booking booking) {
        BookingAnswer answer = new BookingAnswer();
        answer.setId(booking.getId());
        answer.setStart(booking.getStart());
        answer.setEnd(booking.getEnd());
        answer.setStatus(booking.getStatus().name());
        answer.setBooker(UserMapper.userToDto(booking.getBooker()));
        answer.setItem(ItemMapper.itemToDTO(booking.getItem()));
        return answer;
    }

}
