package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.List;

public interface BookingService {
    Booking add(Booking booking, Long userId, Long itemId);

    Booking getById(Long id, Long userId);

    Booking approveBooking(Long bookingId, Boolean approved, Long userId);

    List<Booking> getBookingsByState(Long userId, BookingState state);

    List<Booking> getBookingsByOwner(Long userId, BookingState state);

    Booking getLastBooking(Long itemId);

    Booking getNextBooking(Long itemId);

    List<Booking> getBookingsByItemIdAndBookerInPast(Long itemId, Long userId);

}
