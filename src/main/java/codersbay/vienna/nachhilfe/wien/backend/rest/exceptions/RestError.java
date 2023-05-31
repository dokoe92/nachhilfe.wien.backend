package codersbay.vienna.nachhilfe.wien.backend.rest.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
public class RestError {

    @Setter
    private String message;
    @Setter
    private int httperror;

    public RestError(String message, int httperror) {
        this.message = message;
        this.httperror = httperror;
    }
}
