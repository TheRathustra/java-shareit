package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingAnswer;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.error.UnknownStateException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.item.error.EmptyHeaderException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService service;

    @Autowired
    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping()
    @Transactional
    public BookingAnswer create(@RequestHeader Map<String, String> headers, @RequestBody @Valid BookingDto bookingDto) {
        BookingDto.validate(bookingDto);

        Booking booking = BookingDto.dtoToBooking(bookingDto);

        Long itemId = bookingDto.getItemId();
        Long userId = getUserFromHeaders(headers);
        Booking newBooking = service.add(booking, userId, itemId);
        return BookingAnswer.bookingAnswer(newBooking);
    }

    @PatchMapping("/{bookingId}")
    @Transactional
    public BookingAnswer approveBooking(@RequestHeader Map<String, String> headers, @PathVariable("bookingId") Long bookingId, @RequestParam("approved") Boolean approved) {
        Long userId = getUserFromHeaders(headers);
        Booking booking = service.approveBooking(bookingId, approved, userId);
        return BookingAnswer.bookingAnswer(booking);
    }

    @GetMapping("/{bookingId}")
    public BookingAnswer getBooking(@RequestHeader Map<String, String> headers, @PathVariable("bookingId") Long bookingId) {
        Long userId = getUserFromHeaders(headers);
        Booking booking = service.getById(bookingId, userId);
        return BookingAnswer.bookingAnswer(booking);
    }

    @GetMapping()
    public List<BookingAnswer> getBookingsByState(@RequestHeader Map<String, String> headers, @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO) {
        Long userId = getUserFromHeaders(headers);
        BookingState state;
        try {
            state = BookingState.valueOf(stateDTO);
        } catch (RuntimeException e) {
            throw new UnknownStateException("Unknown state: " + stateDTO);
        }
        List<Booking> bookings = service.getBookingsByState(userId, state);
        return bookings.stream().map(BookingAnswer::bookingAnswer).collect(Collectors.toList());
    }

    @GetMapping("/owner")
    public List<BookingAnswer> getBookingsByOwner(@RequestHeader Map<String, String> headers, @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO) {
        Long userId = getUserFromHeaders(headers);
        BookingState state;
        try {
            state = BookingState.valueOf(stateDTO);
        } catch (RuntimeException e) {
            throw new UnknownStateException("Unknown state: " + stateDTO);
        }
        List<Booking> bookings = service.getBookingsByOwner(userId, state);
        return bookings.stream().map(BookingAnswer::bookingAnswer).collect(Collectors.toList());
    }

    private Long getUserFromHeaders(Map<String, String> headers) {
        String userId = headers.get("x-sharer-user-id");
        if (userId == null) {
            throw new EmptyHeaderException();
        }
        return Long.parseLong(userId);
    }

}
