package codersbay.vienna.nachhilfe.wien.backend.rest.exceptions;

public class UserNotAuthorizedException extends RuntimeException{

    public UserNotAuthorizedException(String message) {
        super(message);
    }
}
