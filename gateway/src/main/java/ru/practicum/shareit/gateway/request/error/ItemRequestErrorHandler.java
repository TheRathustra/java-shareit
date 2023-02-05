package ru.practicum.shareit.gateway.request.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.gateway.global.error.InvalidPageSizeException;
import ru.practicum.shareit.gateway.request.ItemRequestController;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {ItemRequestController.class})
public class ItemRequestErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleItemRequestNotFoundException(final ItemRequestNotFoundException e) {
        return Map.of("error", "ItemRequestNotFoundException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(final IllegalArgumentException e) {
        return Map.of("error", "IllegalArgumentException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidPageSizeException(final InvalidPageSizeException e) {
        return Map.of("error", "InvalidPageSizeException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }

}
