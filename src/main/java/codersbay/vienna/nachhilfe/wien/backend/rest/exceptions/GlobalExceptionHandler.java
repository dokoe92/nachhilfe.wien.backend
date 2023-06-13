package codersbay.vienna.nachhilfe.wien.backend.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

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

    @ExceptionHandler(value = DuplicatedException.class)
    public ResponseEntity<RestError> handleDuplicateIdException(DuplicatedException ex) {
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

    @ExceptionHandler(value= AccessDeniedException.class)
    public ResponseEntity<RestError> handleAccessDeniedException(AuthenticationException ex) {
        RestError restError = new RestError(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(restError, HttpStatus.FORBIDDEN);
    }
}
