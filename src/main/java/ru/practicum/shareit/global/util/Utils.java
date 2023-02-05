package ru.practicum.shareit.global.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.item.error.EmptyHeaderException;

import java.util.Map;

public class Utils {

    public static Long getUserFromHeaders(Map<String, String> headers) {
        String userId = headers.get("x-sharer-user-id");
        if (userId == null) {
            throw new EmptyHeaderException();
        }
        return Long.parseLong(userId);
    }

    public static Pageable getPageRequest(String fromStr, String sizeStr, Sort sort) {
        Pageable pageRequest = null;
        if (!Validator.isEmptyPageSize(fromStr, sizeStr)) {
            int from = Integer.parseInt(fromStr);
            int size = Integer.parseInt(sizeStr);
            Validator.validatePageSize(from, size);

            int page = from / size;
            pageRequest = PageRequest.of(page, size, sort);
        }
        return pageRequest;
    }

    public static Pageable getPageRequest(String fromStr, String sizeStr) {
        Pageable pageRequest = null;
        if (!Validator.isEmptyPageSize(fromStr, sizeStr)) {
            int from = Integer.parseInt(fromStr);
            int size = Integer.parseInt(sizeStr);
            Validator.validatePageSize(from, size);

            int page = from / size;
            pageRequest = PageRequest.of(page, size);
        }
        return pageRequest;
    }

}
