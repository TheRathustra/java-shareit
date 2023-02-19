package ru.practicum.shareit.gateway.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.gateway.booking.client.BookingClient;
import ru.practicum.shareit.gateway.booking.dto.BookingDto;
import ru.practicum.shareit.gateway.booking.error.UnknownStateException;
import ru.practicum.shareit.gateway.booking.model.BookingState;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingClient client;

    @Autowired
    public BookingController(BookingClient client) {
        this.client = client;
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                @RequestBody @Valid BookingDto bookingDto) {
        BookingDto.validate(bookingDto);
        return client.create(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> update(@PathVariable("bookingId") Long bookingId,
                                        @RequestHeader("X-Sharer-User-Id") long userId,
                                        @RequestParam("approved") Boolean approved) {

        return client.update(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable("bookingId") Long bookingId) {
        return client.getBooking(userId, bookingId);
    }

    @GetMapping()
    public ResponseEntity<Object> getBookingsByState(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO,
                                                  @PositiveOrZero @RequestParam(name = "from", required = false) Integer from,
                                                  @Positive @RequestParam(name = "size", required = false) Integer size) {

        BookingState state = BookingState.from(stateDTO)
                .orElseThrow(() -> new UnknownStateException("Unknown state: " + stateDTO));

        return client.getBookings(userId, state, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingsByOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO,
                                                  @PositiveOrZero @RequestParam(name = "from", required = false) Integer from,
                                                  @Positive @RequestParam(name = "size", required = false) Integer size) {

        BookingState state = BookingState.from(stateDTO)
                .orElseThrow(() -> new UnknownStateException("Unknown state: " + stateDTO));

        return client.getBookingsOwner(userId, state, from, size);
    }

}
