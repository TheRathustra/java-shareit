package ru.practicum.shareit.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.user.error.DuplicateEmailException;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {UserController.class, UserStorageImp.class})
public class UserErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleIllegalArgumentException(final DuplicateEmailException e) {
        return Map.of("error", "IllegalArgumentException", "errorMessage", e.getMessage() != null ? e.getMessage() : "");
    }
}
