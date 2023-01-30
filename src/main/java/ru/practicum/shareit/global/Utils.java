package ru.practicum.shareit.global;

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

}
