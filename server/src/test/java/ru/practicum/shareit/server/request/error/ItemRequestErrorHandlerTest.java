package ru.practicum.shareit.server.request.error;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.server.global.error.InvalidPageSizeException;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

class ItemRequestErrorHandlerTest {

    private final ItemRequestErrorHandler handler = new ItemRequestErrorHandler();

    @Test
    void handleItemRequestNotFoundException() {
        Map<String, String> stringStringMap = handler.handleItemRequestNotFoundException(new ItemRequestNotFoundException());
        assertThat(stringStringMap, hasKey("error"));
    }

    @Test
    void handleIllegalArgumentException() {
        Map<String, String> stringStringMap = handler.handleIllegalArgumentException(new IllegalArgumentException());
        assertThat(stringStringMap, hasKey("error"));
    }

    @Test
    void handleInvalidPageSizeException() {
        Map<String, String> stringStringMap = handler.handleInvalidPageSizeException(new InvalidPageSizeException());
        assertThat(stringStringMap, hasKey("error"));
    }
}