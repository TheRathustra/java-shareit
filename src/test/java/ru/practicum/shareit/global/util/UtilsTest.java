package ru.practicum.shareit.global.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.item.error.EmptyHeaderException;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilsTest {

    @Test
    void getUserFromHeaders_whenPresent_thenReturnId() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-sharer-user-id", "1");
        Long userId = Utils.getUserFromHeaders(headers);
        assertThat(userId, equalTo(1L));
    }

    @Test
    void getUserFromHeaders_whenNotPresent_thenThrowEmptyHeaderException() {
        Map<String, String> headers = new HashMap<>();
        Assertions.assertThrows(
                EmptyHeaderException.class,
                () -> Utils.getUserFromHeaders(headers));
    }

    @Test
    void getPageRequest_Sorted() {
        Pageable pageable = Utils.getPageRequest("0", "10", Sort.by(Sort.Direction.DESC,"start"));
        assertNotNull(pageable);
    }

    @Test
    void testGetPageRequest() {
        Pageable pageable = Utils.getPageRequest("0", "10");
        assertNotNull(pageable);
    }
}