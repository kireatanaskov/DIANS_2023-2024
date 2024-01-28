package mk.ukim.finki.authmicroservice.model.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {

    public PasswordsDoNotMatchException() {
        super("The Password and Repeat password fields do not match.");
    }
}

