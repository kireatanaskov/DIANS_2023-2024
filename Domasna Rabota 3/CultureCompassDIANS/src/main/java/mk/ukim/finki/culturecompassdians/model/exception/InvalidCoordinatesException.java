package mk.ukim.finki.culturecompassdians.model.exception;

public class InvalidCoordinatesException extends RuntimeException{

    private String message;

    public InvalidCoordinatesException(String message) {
        super(message);
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
