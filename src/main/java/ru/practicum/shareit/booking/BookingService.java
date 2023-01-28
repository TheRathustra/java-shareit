package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {

    Booking add(Booking booking, Long userId, Long itemId);

    Booking getById(Long id, Long userId);

    Booking approveBooking(Long bookingId, Boolean approved, Long userId);

    List<Booking> getBookingsByState(Long userId, BookingState state);
    List<Booking> getBookingsByOwner(Long userId, BookingState state);

    List<Booking> getBookingsByItemID(Long itemId);

}
