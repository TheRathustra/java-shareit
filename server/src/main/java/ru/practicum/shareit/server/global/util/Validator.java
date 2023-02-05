package ru.practicum.shareit.server.global.util;

import ru.practicum.shareit.server.global.error.InvalidPageSizeException;

public class Validator {

    public static void validatePageSize(int from, int size) {
        if (size <= 0 || from < 0)
            throw new InvalidPageSizeException();
    }

    public static Boolean isEmptyPageSize(Integer from, Integer size) {
        return (from == null && size == null) || (from == 0 && size == 0);
    }

}
