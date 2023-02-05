package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingAnswer;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.error.UnknownStateException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.global.util.Utils;

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
    public BookingAnswer create(@RequestHeader Map<String, String> headers,
                                @RequestBody @Valid BookingDto bookingDto) {
        BookingDto.validate(bookingDto);

        Booking booking = BookingDto.dtoToBooking(bookingDto);

        Long itemId = bookingDto.getItemId();
        Long userId = Utils.getUserFromHeaders(headers);
        Booking newBooking = service.add(booking, userId, itemId);
        return BookingAnswer.bookingAnswer(newBooking);
    }

    @PatchMapping("/{bookingId}")
    @Transactional
    public BookingAnswer approveBooking(@RequestHeader Map<String, String> headers, @PathVariable("bookingId") Long bookingId,
                                        @RequestParam("approved") Boolean approved) {
        Long userId = Utils.getUserFromHeaders(headers);
        Booking booking = service.approveBooking(bookingId, approved, userId);
        return BookingAnswer.bookingAnswer(booking);
    }

    @GetMapping("/{bookingId}")
    public BookingAnswer getBooking(@RequestHeader Map<String, String> headers, @PathVariable("bookingId") Long bookingId) {
        Long userId = Utils.getUserFromHeaders(headers);
        Booking booking = service.getById(bookingId, userId);
        return BookingAnswer.bookingAnswer(booking);
    }

    @GetMapping()
    public List<BookingAnswer> getBookingsByState(@RequestHeader Map<String, String> headers,
                                                  @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO,
                                                  @RequestParam(name = "from", required = false) String fromStr,
                                                  @RequestParam(name = "size", required = false) String sizeStr) {

        Pageable pageRequest = Utils.getPageRequest(fromStr, sizeStr, Sort.by(Sort.Direction.DESC,"start"));

        Long userId = Utils.getUserFromHeaders(headers);
        BookingState state;
        try {
            state = BookingState.valueOf(stateDTO);
        } catch (RuntimeException e) {
            throw new UnknownStateException("Unknown state: " + stateDTO);
        }
        List<Booking> bookings = service.getBookingsByState(userId, state, pageRequest);
        return bookings.stream().map(BookingAnswer::bookingAnswer).collect(Collectors.toList());
    }

    @GetMapping("/owner")
    public List<BookingAnswer> getBookingsByOwner(@RequestHeader Map<String, String> headers,
                                                  @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO,
                                                  @RequestParam(name = "from", required = false) String fromStr,
                                                  @RequestParam(name = "size", required = false) String sizeStr) {

        Pageable pageRequest = Utils.getPageRequest(fromStr, sizeStr, Sort.by(Sort.Direction.DESC,"start"));

        Long userId = Utils.getUserFromHeaders(headers);
        BookingState state;
        try {
            state = BookingState.valueOf(stateDTO);
        } catch (RuntimeException e) {
            throw new UnknownStateException("Unknown state: " + stateDTO);
        }
        List<Booking> bookings = service.getBookingsByOwner(userId, state, pageRequest);
        return bookings.stream().map(BookingAnswer::bookingAnswer).collect(Collectors.toList());
    }

}
