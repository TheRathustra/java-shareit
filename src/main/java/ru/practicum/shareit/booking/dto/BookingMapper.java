package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.user.dto.UserMapper;

public class BookingMapper {

    public static BookingDto BookingToDTO(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        dto.setStatus(booking.getStatus());
        if (booking.getItem() != null)
            dto.setItemId(booking.getItem().getId());
        if (booking.getBooker() != null)
            dto.setBooker(UserMapper.userToDto(booking.getBooker()));

        return dto;
    }

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
