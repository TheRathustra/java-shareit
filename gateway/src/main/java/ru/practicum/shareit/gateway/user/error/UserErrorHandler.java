package ru.practicum.shareit.gateway.user.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.gateway.user.UserController;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {UserController.class})
public class UserErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(final IllegalArgumentException e) {
        return Map.of("error", "IllegalArgumentException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }
}
