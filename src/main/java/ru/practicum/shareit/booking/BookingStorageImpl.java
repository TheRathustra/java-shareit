package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingRepository;

import java.util.List;
import java.util.Optional;

@Component
public class BookingStorageImpl implements BookingStorage {

    private BookingRepository repository;

    @Autowired
    public BookingStorageImpl(BookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Booking save(Booking booking) {
        return repository.saveAndFlush(booking);
    }

    @Override
    public Booking getById(Long id) {
        Optional<Booking> booking = repository.findById(id);
        if (booking.isEmpty())
            throw new IllegalArgumentException();
        return booking.get();
    }

    public List<Booking> getBookingsByItemId(Long itemId) {
        Specification<Booking> spec = BookingSpecs.byItem(itemId);
        List<Booking> bookings = repository.findAll(spec, Sort.by(Sort.Direction.ASC,"start"));
        return bookings;
    }

    public List<Booking> getBookingsByItemIdAndBookerInPast(Long itemId, Long userId) {
        Specification<Booking> spec = BookingSpecs.byItem(itemId);
        spec = spec.and(BookingSpecs.byBooker(userId));
        spec = spec.and(BookingSpecs.byStatus(BookingStatus.APPROVED));
        spec = spec.and(BookingSpecs.past());
        List<Booking> bookings = repository.findAll(spec, Sort.by(Sort.Direction.ASC,"start"));
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByState(Specification<Booking> spec, BookingState state) {

        if (state == BookingState.PAST)
            spec = spec.and(BookingSpecs.past());
        if (state == BookingState.FUTURE)
            spec = spec.and(BookingSpecs.future());
        if (state == BookingState.CURRENT)
            spec = spec.and(BookingSpecs.current());
        if (state == BookingState.WAITING)
            spec = spec.and(BookingSpecs.byStatus(BookingStatus.WAITING));
        if (state == BookingState.REJECTED)
            spec = spec.and(BookingSpecs.byStatus(BookingStatus.REJECTED));

        List<Booking> bookings = repository.findAll(spec, Sort.by(Sort.Direction.DESC,"start"));

        return bookings;
    }
}
