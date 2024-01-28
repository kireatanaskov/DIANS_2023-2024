package mk.ukim.finki.authmicroservice.model.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String username) {
        super(String.format("Username %s already exists", username));
    }
}
