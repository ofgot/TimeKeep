package sir.timekeep.exception;

public class UserNotAllowedException extends RuntimeException {

    public UserNotAllowedException() {}

    public UserNotAllowedException(String message) {
        super(message);
    }
}
