package mk.ukim.finki.culturecompassdians.model.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String username) {
        super(String.format("Username %s already exists", username));
    }
}
