package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingSpecs;
import ru.practicum.shareit.booking.error.ItemUnavailableException;
import ru.practicum.shareit.booking.error.UpdateBookingException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.error.UndefinedUserException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserService;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingStorage storage;
    private final UserService userService;
    private final ItemStorage itemStorage;

    @Autowired
    public BookingServiceImpl(BookingStorage storage, UserService userService, ItemStorage itemStorage) {
        this.storage = storage;
        this.userService = userService;
        this.itemStorage = itemStorage;
    }

    @Override
    public Booking add(Booking booking, Long userId, Long itemId) {

        Item item = itemStorage.getItemById(itemId);
        if (!item.getAvailable()) {
            throw new ItemUnavailableException();
        }
        User user = userService.getUserById(userId);

        if (item.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException();
        }

        booking.setStatus(BookingStatus.WAITING);
        booking.setItem(item);
        booking.setBooker(user);

        return storage.save(booking);
    }

    @Override
    public Booking getById(Long id, Long userId) {
        Booking booking = storage.getById(id);

        Long ownerId = booking.getItem().getOwner().getId();
        Long bookerId = booking.getBooker().getId();

        if (!ownerId.equals(userId) && !bookerId.equals(userId))
            throw new IllegalArgumentException();

        return booking;
    }

    @Override
    public Booking approveBooking(Long bookingId, Boolean approved, Long userId) {
        Booking booking = storage.getById(bookingId);
        Long bookerId = booking.getBooker().getId();
        Long ownerId = booking.getItem().getOwner().getId();

        BookingStatus status;
        if (userId.equals(bookerId)) {
            if (!approved) {
                status = BookingStatus.CANCELED;
            } else {
                throw new IllegalArgumentException();
            }
        } else if (ownerId.equals(userId)) {
            if (approved) {
                status = BookingStatus.APPROVED;
            } else {
                status = BookingStatus.REJECTED;
            }
        } else {
            throw new IllegalArgumentException();
        }

        if (booking.getStatus() != BookingStatus.WAITING) {
            throw new UpdateBookingException();
        }

        booking.setStatus(status);
        booking = storage.save(booking);
        return booking;
    }

    @Override
    public List<Booking> getBookingsByState(Long userId, BookingState state) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UndefinedUserException();
        }
        Specification<Booking> spec = BookingSpecs.byBooker(userId);
        return storage.getBookingsByState(spec, state);
    }

    @Override
    public List<Booking> getBookingsByOwner(Long userId, BookingState state) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UndefinedUserException();
        }
        Specification<Booking> spec = BookingSpecs.byOwner(userId);
        return storage.getBookingsByState(spec, state);
    }

}
