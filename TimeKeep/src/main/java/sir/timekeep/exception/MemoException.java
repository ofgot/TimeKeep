package sir.timekeep.exception;

public class MemoException extends RuntimeException{
    public MemoException() {
    }

    public MemoException(String message) {
        super(message);
    }

    public MemoException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemoException(Throwable cause) {
        super(cause);
    }

}
