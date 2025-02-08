package exception;

public class CourseAlreadyEnrolledException extends Exception {
    public CourseAlreadyEnrolledException(String message) {
        super(message);
    }
}
