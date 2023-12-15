package mk.ukim.finki.culturecompassdians.model.exception;

public class NodeAlreadyExistsException extends RuntimeException{

    private String message;

    public NodeAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
