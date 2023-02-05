package ru.practicum.shareit.gateway.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.gateway.booking.client.BookingClient;
import ru.practicum.shareit.gateway.booking.dto.BookingDto;
import ru.practicum.shareit.gateway.booking.error.UnknownStateException;
import ru.practicum.shareit.gateway.booking.model.BookingState;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingClient client;

    @Autowired
    public BookingController(BookingClient client) {
        this.client = client;
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                @RequestBody @Valid BookingDto bookingDto) {
        BookingDto.validate(bookingDto);

        ResponseEntity<Object> newBooking = client.create(userId, bookingDto);
        return newBooking;
    }

    @PatchMapping("/{bookingId}")
    @Transactional
    public ResponseEntity<Object> update(@PathVariable("bookingId") Long bookingId,
                                        @RequestHeader("X-Sharer-User-Id") long userId,
                                        @RequestParam("approved") Boolean approved) {

        ResponseEntity<Object> booking = client.update(userId, bookingId, approved);
        return booking;
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable("bookingId") Long bookingId) {
        ResponseEntity<Object> booking = client.getBooking(userId, bookingId);
        return booking;
    }

    @GetMapping()
    public ResponseEntity<Object> getBookingsByState(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO,
                                                  @PositiveOrZero @RequestParam(name = "from", required = false) Integer from,
                                                  @Positive @RequestParam(name = "size", required = false) Integer size) {

        BookingState state = BookingState.from(stateDTO)
                .orElseThrow(() -> new UnknownStateException("Unknown state: " + stateDTO));

        ResponseEntity<Object> bookings = client.getBookings(userId, state, from, size);
        return bookings;
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingsByOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO,
                                                  @PositiveOrZero @RequestParam(name = "from", required = false) Integer from,
                                                  @Positive @RequestParam(name = "size", required = false) Integer size) {

        BookingState state = BookingState.from(stateDTO)
                .orElseThrow(() -> new UnknownStateException("Unknown state: " + stateDTO));

        ResponseEntity<Object> bookings = client.getBookingsOwner(userId, state, from, size);
        return bookings;
    }

}
