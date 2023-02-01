package ru.practicum.shareit.global.util;

import ru.practicum.shareit.global.error.InvalidPageSizeException;

public class Validator {

    public static void validatePageSize(int from, int size) {
        if (size <= 0 || from < 0)
            throw new InvalidPageSizeException();
    }

    public static Boolean isEmptyPageSize(String from, String size) {
        return from == null && size == null;
    }

}
