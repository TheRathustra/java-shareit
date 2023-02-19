package ru.practicum.shareit.server.user.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.server.user.UserController;
import ru.practicum.shareit.server.user.service.UserServiceImpl;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {UserController.class, UserServiceImpl.class})
public class UserErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(final IllegalArgumentException e) {
        return Map.of("error", "IllegalArgumentException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUndefinedUserException(final UndefinedUserException e) {
        return Map.of("error", "UndefinedUserException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }
}
