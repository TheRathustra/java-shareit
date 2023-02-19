package ru.practicum.shareit.gateway.global.util;

import ru.practicum.shareit.gateway.global.error.InvalidPageSizeException;

public class Validator {

    public static void validatePageSize(Integer from, Integer size) {
        if (isEmptyPageSize(from, size))
            return;
        if (size <= 0 || from < 0)
            throw new InvalidPageSizeException();
    }

    public static Boolean isEmptyPageSize(Integer from, Integer size) {
        return (from == null && size == null);
    }

}
