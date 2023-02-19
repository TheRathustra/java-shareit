package ru.practicum.shareit.server.user.error;

public class UndefinedUserException extends RuntimeException {

    public UndefinedUserException(String message) {
        super(message);
    }

    public UndefinedUserException() {
    }

}
