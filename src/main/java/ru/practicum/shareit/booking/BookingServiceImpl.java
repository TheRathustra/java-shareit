package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingSpecs;
import ru.practicum.shareit.booking.error.ItemUnavailableException;
import ru.practicum.shareit.booking.error.UpdateBookingException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.error.UndefinedUserException;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository repository, UserService userService, ItemRepository itemRepository) {
        this.repository = repository;
        this.userService = userService;
        this.itemRepository = itemRepository;
    }

    @Override
    public Booking add(Booking booking, Long userId, Long itemId) {

        Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (itemOptional.isEmpty())
            throw new IllegalArgumentException();

        Item item = itemOptional.get();

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

        repository.saveAndFlush(booking);
        return booking;
    }

    @Override
    public Booking getById(Long id, Long userId) {

        Booking booking = getByIdFromRepository(id);

        Long ownerId = booking.getItem().getOwner().getId();
        Long bookerId = booking.getBooker().getId();

        if (!ownerId.equals(userId) && !bookerId.equals(userId))
            throw new IllegalArgumentException();

        return booking;
    }

    @Override
    public Booking approveBooking(Long bookingId, Boolean approved, Long userId) {
        Booking booking = getByIdFromRepository(bookingId);
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
        booking = repository.saveAndFlush(booking);
        return booking;
    }

    @Override
    public List<Booking> getBookingsByState(Long userId, BookingState state, Pageable pageRequest) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UndefinedUserException();
        }
        Specification<Booking> spec = BookingSpecs.byBooker(userId);
        spec = spec.and(BookingSpecs.byState(state));

        List<Booking> bookings;
        if (pageRequest == null) {
            bookings = repository.findAll(spec, Sort.by(Sort.Direction.DESC,"start"));
        } else {
            bookings = repository.findAll(spec, pageRequest).toList();
        }

        return bookings;
    }

    @Override
    public List<Booking> getBookingsByOwner(Long userId, BookingState state, Pageable pageRequest) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UndefinedUserException();
        }
        Specification<Booking> spec = BookingSpecs.byOwner(userId);
        spec = spec.and(BookingSpecs.byState(state));

        List<Booking> bookings;
        if (pageRequest == null) {
            bookings = repository.findAll(spec, Sort.by(Sort.Direction.DESC,"start"));
        } else {
            bookings = repository.findAll(spec, pageRequest).toList();
        }

        return bookings;
    }

    private Booking getByIdFromRepository(Long id) {
        Optional<Booking> booking = repository.findById(id);
        if (booking.isEmpty())
            throw new IllegalArgumentException();
        return booking.get();
    }

    public Booking getLastBooking(Long itemId) {
        Booking booking = null;
        Specification<Booking> spec = BookingSpecs.byItem(itemId);
        spec = spec.and(BookingSpecs.byStatus(BookingStatus.APPROVED));
        spec = spec.and(BookingSpecs.past());
        List<Booking> bookings = repository.findAll(spec, Sort.by(Sort.Direction.DESC,"start"));
        if (!bookings.isEmpty())
            booking = bookings.get(0);
        return booking;
    }

    public Booking getNextBooking(Long itemId) {
        Booking booking = null;
        Specification<Booking> spec = BookingSpecs.byItem(itemId);
        spec = spec.and(BookingSpecs.byStatus(BookingStatus.APPROVED));
        spec = spec.and(BookingSpecs.future());
        List<Booking> bookings = repository.findAll(spec, Sort.by(Sort.Direction.ASC,"start"));
        if (!bookings.isEmpty())
            booking = bookings.get(0);
        return booking;
    }

    public List<Booking> getBookingsByItemIdAndBookerInPast(Long itemId, Long userId) {
        Specification<Booking> spec = BookingSpecs.byItem(itemId);
        spec = spec.and(BookingSpecs.byBooker(userId));
        spec = spec.and(BookingSpecs.byStatus(BookingStatus.APPROVED));
        spec = spec.and(BookingSpecs.past());
        return repository.findAll(spec, Sort.by(Sort.Direction.ASC,"start"));
    }

}
