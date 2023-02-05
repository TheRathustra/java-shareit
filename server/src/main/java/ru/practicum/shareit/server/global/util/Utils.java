package ru.practicum.shareit.server.global.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.server.item.error.EmptyHeaderException;

import java.util.Map;

public class Utils {

    public static Long getUserFromHeaders(Map<String, String> headers) {
        String userId = headers.get("x-sharer-user-id");
        if (userId == null) {
            throw new EmptyHeaderException();
        }
        return Long.parseLong(userId);
    }

    public static Pageable getPageRequest(Integer from, Integer size, Sort sort) {
        Pageable pageRequest = null;
        if (!Validator.isEmptyPageSize(from, size)) {
            Validator.validatePageSize(from, size);

            int page = from / size;
            pageRequest = PageRequest.of(page, size, sort);
        }
        return pageRequest;
    }

    public static Pageable getPageRequest(Integer from, Integer size) {
        Pageable pageRequest = null;
        if (!Validator.isEmptyPageSize(from, size)) {
            Validator.validatePageSize(from, size);

            int page = from / size;
            pageRequest = PageRequest.of(page, size);
        }
        return pageRequest;
    }

}
