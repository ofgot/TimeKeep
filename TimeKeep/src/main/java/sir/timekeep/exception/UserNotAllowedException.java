package sir.timekeep.exception;

public class UserNotAllowedException extends MemoException {

    public UserNotAllowedException() {}

    public UserNotAllowedException(String message) {
        super(message);
    }
}
