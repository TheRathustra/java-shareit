package ru.practicum.shareit.gateway.booking.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.gateway.booking.BookingController;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {BookingController.class})
public class BookingErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleItemUnavailableException(final ItemUnavailableException e) {
        return Map.of("error", "ItemUnavailableException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidBookingException(final InvalidBookingException e) {
        return Map.of("error", "InvalidBookingException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUnknownStateException(final UnknownStateException e) {
        String message = e.getMessage() != null ? e.getMessage() : "";
        return Map.of("error", message, "errorMessage", message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUpdateBookingException(final UpdateBookingException e) {
        String message = e.getMessage() != null ? e.getMessage() : "";
        return Map.of("error", message, "errorMessage", message);
    }

}
