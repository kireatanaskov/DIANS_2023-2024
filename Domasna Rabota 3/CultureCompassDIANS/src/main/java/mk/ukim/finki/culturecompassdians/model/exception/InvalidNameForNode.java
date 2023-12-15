package mk.ukim.finki.culturecompassdians.model.exception;

public class InvalidNameForNode extends RuntimeException{
    private String message;

    public InvalidNameForNode(String message) {
        super(message);
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
