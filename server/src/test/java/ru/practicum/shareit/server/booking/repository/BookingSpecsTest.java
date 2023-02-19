package ru.practicum.shareit.server.booking.repository;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.shareit.server.booking.model.Booking;
import ru.practicum.shareit.server.booking.model.BookingState;
import ru.practicum.shareit.server.booking.model.BookingStatus;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.user.model.User;

import static org.junit.jupiter.api.Assertions.*;

class BookingSpecsTest {

    private final User user = new User(
            1L,
            "userName",
            "user@email.com"
    );

    private final Item item = new Item(
            1L,
            "test",
            "test",
            true,
            user,
            1L
    );

    @Test
    void byBooker() {
        Specification<Booking> spec = BookingSpecs.byBooker(user.getId());
        assertNotNull(spec);
    }

    @Test
    void byOwner() {
        Specification<Booking> spec = BookingSpecs.byOwner(user.getId());
        assertNotNull(spec);
    }

    @Test
    void byItem() {
        Specification<Booking> spec = BookingSpecs.byItem(item.getId());
        assertNotNull(spec);
    }

    @Test
    void byStatus() {
        Specification<Booking> spec = BookingSpecs.byStatus(BookingStatus.WAITING);
        assertNotNull(spec);
    }

    @Test
    void past() {
        Specification<Booking> spec = BookingSpecs.past();
        assertNotNull(spec);
    }

    @Test
    void future() {
        Specification<Booking> spec = BookingSpecs.future();
        assertNotNull(spec);
    }

    @Test
    void current() {
        Specification<Booking> spec = BookingSpecs.current();
        assertNotNull(spec);
    }

    @Test
    void byState() {
        Specification<Booking> spec = BookingSpecs.byState(BookingState.PAST);
        assertNotNull(spec);
        spec = BookingSpecs.byState(BookingState.FUTURE);
        assertNotNull(spec);
        spec = BookingSpecs.byState(BookingState.CURRENT);
        assertNotNull(spec);
        spec = BookingSpecs.byState(BookingState.WAITING);
        assertNotNull(spec);
        spec = BookingSpecs.byState(BookingState.REJECTED);
        assertNotNull(spec);
    }
}