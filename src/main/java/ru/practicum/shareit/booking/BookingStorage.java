package ru.practicum.shareit.booking;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.List;

public interface BookingStorage {
    Booking save(Booking booking);

    Booking getById(Long id);

    List<Booking> getBookingsByState(Specification<Booking> spec, BookingState state);

    List<Booking> getBookingsByItemIdAndBookerInPast(Long itemId, Long userId);

    Booking getLastBooking(Long itemId);

    Booking getNextBooking(Long itemId);
}
