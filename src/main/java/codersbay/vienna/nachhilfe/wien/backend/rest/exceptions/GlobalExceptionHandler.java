package codersbay.vienna.nachhilfe.wien.backend.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ProfileNotFoundException.class)
    public ResponseEntity<RestError> handleProfileNotFoundException(ProfileNotFoundException ex) {
        RestError restError = new RestError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(restError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<RestError> handleUserNotFoundException(UserNotFoundException ex) {
        RestError restError = new RestError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(restError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DuplicateCoachingException.class)
    public ResponseEntity<RestError> handleDuplicateCoachingException (DuplicateCoachingException ex) {
        RestError restError = new RestError(ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(restError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = DuplicateIdException.class)
    public ResponseEntity<RestError> handleDuplicateIdException(DuplicateIdException ex) {
        RestError restError = new RestError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(restError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingIdException.class)
    public ResponseEntity<RestError> handleMissingIdException(MissingIdException ex) {
        RestError restError = new RestError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(restError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<RestError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        RestError restError = new RestError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(restError, HttpStatus.NOT_FOUND);

    }
}
