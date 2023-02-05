package ru.practicum.shareit.server.user.error;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

class UserErrorHandlerTest {

    private final UserErrorHandler handler = new UserErrorHandler();

    @Test
    void handleIllegalArgumentException() {
        Map<String, String> stringStringMap = handler.handleIllegalArgumentException(new IllegalArgumentException());
        assertThat(stringStringMap, hasKey("error"));
    }
}