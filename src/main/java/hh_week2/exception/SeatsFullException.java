package hh_week2.exception;

public class SeatsFullException extends RuntimeException {
    public SeatsFullException(String message) {
        super(message);
    }
}
