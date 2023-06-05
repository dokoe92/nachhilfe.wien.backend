package codersbay.vienna.nachhilfe.wien.backend.rest.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
