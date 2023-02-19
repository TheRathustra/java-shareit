package ru.practicum.shareit.server.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.server.booking.dto.BookingAnswer;
import ru.practicum.shareit.server.booking.dto.BookingDto;
import ru.practicum.shareit.server.booking.model.Booking;
import ru.practicum.shareit.server.booking.model.BookingState;
import ru.practicum.shareit.server.booking.service.BookingService;
import ru.practicum.shareit.server.global.util.Utils;

import java.util.List;
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
    public BookingAnswer create(@RequestHeader("X-Sharer-User-Id") long userId,
                                @RequestBody BookingDto bookingDto) {
        BookingDto.validate(bookingDto);

        Booking booking = BookingDto.dtoToBooking(bookingDto);

        Long itemId = bookingDto.getItemId();
        Booking newBooking = service.add(booking, userId, itemId);
        return BookingAnswer.bookingAnswer(newBooking);
    }

    @PatchMapping("/{bookingId}")
    @Transactional
    public BookingAnswer approveBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                        @PathVariable("bookingId") Long bookingId,
                                        @RequestParam("approved") Boolean approved) {
        Booking booking = service.approveBooking(bookingId, approved, userId);
        return BookingAnswer.bookingAnswer(booking);
    }

    @GetMapping("/{bookingId}")
    public BookingAnswer getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @PathVariable("bookingId") Long bookingId) {
        Booking booking = service.getById(bookingId, userId);
        return BookingAnswer.bookingAnswer(booking);
    }

    @GetMapping()
    public List<BookingAnswer> getBookingsByState(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO,
                                                  @RequestParam(name = "from", required = false) Integer from,
                                                  @RequestParam(name = "size", required = false) Integer size) {

        Pageable pageRequest = Utils.getPageRequest(from, size, Sort.by(Sort.Direction.DESC,"start"));

        BookingState state = BookingState.from(stateDTO).get();
        List<Booking> bookings = service.getBookingsByState(userId, state, pageRequest);
        return bookings.stream().map(BookingAnswer::bookingAnswer).collect(Collectors.toList());
    }

    @GetMapping("/owner")
    public List<BookingAnswer> getBookingsByOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO,
                                                  @RequestParam(name = "from", required = false) Integer from,
                                                  @RequestParam(name = "size", required = false) Integer size) {

        Pageable pageRequest = Utils.getPageRequest(from, size, Sort.by(Sort.Direction.DESC,"start"));

        BookingState state = BookingState.from(stateDTO).get();
        List<Booking> bookings = service.getBookingsByOwner(userId, state, pageRequest);
        return bookings.stream().map(BookingAnswer::bookingAnswer).collect(Collectors.toList());
    }

}
