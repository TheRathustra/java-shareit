package ru.practicum.shareit.booking.error;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

class BookingErrorHandlerTest {

    private final BookingErrorHandler handler = new BookingErrorHandler();

    @Test
    void handleItemUnavailableException() {
        Map<String, String> stringStringMap = handler.handleItemUnavailableException(new ItemUnavailableException());
        assertThat(stringStringMap, hasKey("error"));
    }

    @Test
    void handleInvalidBookingException() {
        Map<String, String> stringStringMap = handler.handleInvalidBookingException(new InvalidBookingException());
        assertThat(stringStringMap, hasKey("error"));
    }

    @Test
    void handleUnknownStateException() {
        Map<String, String> stringStringMap = handler.handleUnknownStateException(new UnknownStateException("error"));
        assertThat(stringStringMap, hasKey("error"));
    }

    @Test
    void handleUpdateBookingException() {
        Map<String, String> stringStringMap = handler.handleUpdateBookingException(new UpdateBookingException());
        assertThat(stringStringMap, hasKey("error"));
    }
}