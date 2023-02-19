package ru.practicum.shareit.server.global.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Utils {

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
