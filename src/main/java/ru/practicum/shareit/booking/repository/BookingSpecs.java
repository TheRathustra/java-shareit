package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;

public class BookingSpecs {
    public static Specification<Booking> byBooker(Long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("booker").get("id"), userId);

    }

    public static Specification<Booking> byOwner(Long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("item").get("owner").get("id"), userId);

    }

    public static Specification<Booking> byItem(Long itemId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("item").get("id"), itemId);

    }

    public static Specification<Booking> byStatus(BookingStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);

    }

    public static Specification<Booking> past() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("end"), LocalDateTime.now());
    }

    public static Specification<Booking> future() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("start"), LocalDateTime.now());
    }

    public static Specification<Booking> current() {
        Specification<Booking> spec = Specification.where(null);
        spec = spec.and((Specification<Booking>) (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("start"), LocalDateTime.now()));
        spec = spec.and((Specification<Booking>) (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("end"), LocalDateTime.now()));
        return spec;
    }

    public static Specification<Booking> byState(BookingState state) {
        Specification<Booking> spec = Specification.where(null);
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
        return spec;
    }

}
