package ru.practicum.shareit.gateway.booking.error;

public class UnknownStateException extends RuntimeException {
    String message;

    public UnknownStateException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
