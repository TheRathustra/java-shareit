package ru.practicum.shareit.server.global.util;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilsTest {

    @Test
    void getPageRequest_Sorted() {
        Pageable pageable = Utils.getPageRequest(0, 10, Sort.by(Sort.Direction.DESC,"start"));
        assertNotNull(pageable);
    }

    @Test
    void testGetPageRequest() {
        Pageable pageable = Utils.getPageRequest(0, 10);
        assertNotNull(pageable);
    }
}