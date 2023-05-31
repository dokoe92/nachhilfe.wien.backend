package codersbay.vienna.nachhilfe.wien.backend.rest.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
