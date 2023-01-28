package ru.practicum.shareit.booking;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BookingStorage {
    Booking save(Booking booking);

    Booking getById(Long id);

    List<Booking> getBookingsByState(Specification<Booking> spec, BookingState state);
    List<Booking> getBookingsByItemId(Long itemId);

    List<Booking> getBookingsByItemIdAndBookerInPast(Long itemId, Long userId);
}
