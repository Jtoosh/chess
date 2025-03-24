package client;

public class AlreadyInUseException extends RuntimeException {
    public AlreadyInUseException(String message) {
        super(message);
    }
}
