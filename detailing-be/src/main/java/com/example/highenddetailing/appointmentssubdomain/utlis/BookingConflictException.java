package com.example.highenddetailing.appointmentssubdomain.utlis;

public class BookingConflictException extends RuntimeException {
    public BookingConflictException() {
        super();
    }

    public BookingConflictException(String message) {
        super(message);
    }

    public BookingConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingConflictException(Throwable cause) {
        super(cause);
    }
}
