package codersbay.vienna.nachhilfe.wien.backend.rest.exceptions;

import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
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
}
