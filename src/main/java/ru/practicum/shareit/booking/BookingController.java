package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingAnswer;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.error.UnknownStateException;
import ru.practicum.shareit.item.error.EmptyHeaderException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private BookingService service;

    @Autowired
    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping()
    public BookingAnswer create(@RequestHeader Map<String, String> headers, @RequestBody @Valid BookingDto bookingDto) {
        BookingDto.validate(bookingDto);

        Booking booking = BookingMapper.dtoToBooking(bookingDto);

        Long itemId = bookingDto.getItemId();
        Long userId = getUserFromHeaders(headers);
        Booking newBooking = service.add(booking, userId, itemId);
        return BookingMapper.bookingAnswer(newBooking);
    }

    @PatchMapping("/{bookingId}")
    public BookingAnswer approveBooking(@RequestHeader Map<String, String> headers, @PathVariable("bookingId") Long bookingId, @RequestParam("approved") Boolean approved) {
        Long userId = getUserFromHeaders(headers);
        Booking booking = service.approveBooking(bookingId, approved, userId);
        BookingAnswer answer = BookingMapper.bookingAnswer(booking);
        return answer;
    }

    @GetMapping("/{bookingId}")
    public BookingAnswer getBooking(@RequestHeader Map<String, String> headers, @PathVariable("bookingId") Long bookingId) {
        Long userId = getUserFromHeaders(headers);
        Booking booking = service.getById(bookingId, userId);
        BookingAnswer answer = BookingMapper.bookingAnswer(booking);
        return answer;
    }


    @GetMapping()
    public List<BookingAnswer> getBookingsByState(@RequestHeader Map<String, String> headers, @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO) {
        Long userId = getUserFromHeaders(headers);
        BookingState state = null;
        try {
            state = BookingState.valueOf(stateDTO);
        } catch (RuntimeException e) {
            throw new UnknownStateException("Unknown state: " + stateDTO);
        }

        List<Booking> bookings = service.getBookingsByState(userId, state);
        List<BookingAnswer> answer = bookings.stream().map(BookingMapper::bookingAnswer).collect(Collectors.toList());
        return answer;
    }

    @GetMapping("/owner")
    public List<BookingAnswer> getBookingsByOwner(@RequestHeader Map<String, String> headers, @RequestParam(name = "state", required = false, defaultValue = "ALL") String stateDTO) {
        Long userId = getUserFromHeaders(headers);
        BookingState state = null;
        try {
            state = BookingState.valueOf(stateDTO);
        } catch (RuntimeException e) {
            throw new UnknownStateException("Unknown state: " + stateDTO);
        }
        List<Booking> bookings = service.getBookingsByOwner(userId, state);
        List<BookingAnswer> answer = bookings.stream().map(BookingMapper::bookingAnswer).collect(Collectors.toList());
        return answer;
    }

    private Long getUserFromHeaders(Map<String, String> headers) {
        String userId = headers.get("x-sharer-user-id");
        if (userId == null) {
            throw new EmptyHeaderException();
        }
        long id = Long.parseLong(userId);
        return id;
    }

}
