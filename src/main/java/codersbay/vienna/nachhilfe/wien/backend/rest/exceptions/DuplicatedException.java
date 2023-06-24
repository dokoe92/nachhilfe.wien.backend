package codersbay.vienna.nachhilfe.wien.backend.rest.exceptions;

public class DuplicatedException extends RuntimeException{

    public DuplicatedException(String message) {
        super(message);
    }
}
