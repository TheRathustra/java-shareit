package ru.practicum.shareit.item;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.item.error.EmptyHeaderException;
import ru.practicum.shareit.item.error.UserNotFoundException;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {ItemController.class, ItemServiceImpl.class})
public class ItemErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFoundException(final UserNotFoundException e) {
        return Map.of("error", "UserNotFoundException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleEmptyHeaderException(final EmptyHeaderException e) {
        return Map.of("error", "EmptyHeaderException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }

}
