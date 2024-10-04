package hh_week2.exception;

public class AlreadyEnrolledInLectureException extends RuntimeException {
    public AlreadyEnrolledInLectureException(String message) {
        super(message);
    }
}
