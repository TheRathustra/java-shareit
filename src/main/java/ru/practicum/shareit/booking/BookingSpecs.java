package ru.practicum.shareit.booking;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class BookingSpecs {
    public static Specification<Booking> byBooker(Long userId) {
        return (Specification<Booking>) (root, query, criteriaBuilder) ->
                 criteriaBuilder.equal(root.get("booker").get("id"), userId);

    }

    public static Specification<Booking> byOwner(Long userId) {
        return (Specification<Booking>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("item").get("owner").get("id"), userId);

    }

    public static Specification<Booking> byItem(Long itemId) {
        return (Specification<Booking>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("item").get("id"), itemId);

    }

    public static Specification<Booking> byStatus(BookingStatus status) {
        return (Specification<Booking>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);

    }

    public static Specification<Booking> past() {
        return (Specification<Booking>) (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("end"), LocalDateTime.now());
    }

    public static Specification<Booking> future() {
        return (Specification<Booking>) (root, query, criteriaBuilder) ->
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
}
