package ru.practicum.shareit.global.error;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

class ErrorHandlerTest {

    private final ErrorHandler handler = new ErrorHandler();

    @Test
    void handleIllegalArgumentException() {
        Map<String, String> stringStringMap = handler.handleIllegalArgumentException(new IllegalArgumentException());
        assertThat(stringStringMap, hasKey("error"));
    }
}