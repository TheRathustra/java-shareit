package ru.practicum.shareit.server.item.error;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

class ItemErrorHandlerTest {

    private final ItemErrorHandler handler = new ItemErrorHandler();

    @Test
    void handleUserNotFoundException() {
        Map<String, String> stringStringMap = handler.handleUserNotFoundException(new UserNotFoundException());
        assertThat(stringStringMap, hasKey("error"));
    }

    @Test
    void handleEmptyHeaderException() {
        Map<String, String> stringStringMap = handler.handleEmptyHeaderException(new EmptyHeaderException());
        assertThat(stringStringMap, hasKey("error"));
    }

    @Test
    void handleCommentWithoutBooking() {
        Map<String, String> stringStringMap = handler.handleCommentWithoutBooking(new CommentWithoutBooking());
        assertThat(stringStringMap, hasKey("error"));
    }
}