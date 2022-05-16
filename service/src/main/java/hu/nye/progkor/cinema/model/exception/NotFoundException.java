package hu.nye.progkor.cinema.model.exception;

import java.io.Serial;

/**
 * Exception for not foundable movies.
 */
public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2974108486715092114L;

    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}