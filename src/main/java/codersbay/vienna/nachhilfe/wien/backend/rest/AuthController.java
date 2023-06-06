package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.model.Pojo.Authentication.AuthRequest;
import codersbay.vienna.nachhilfe.wien.backend.model.Pojo.Authentication.AuthResponse;
import codersbay.vienna.nachhilfe.wien.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Retrieves an authentication response for the provided email and password.
     *
     * @param authRequest  the AuthRequest object containing the email and password
     * @return ResponseEntity with the AuthResponse object and HTTP status OK
     */
    @PostMapping
    public ResponseEntity<AuthResponse> getAuthResponse(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.findByEmailAndPassword(authRequest.getEmail(), authRequest.getPassword());
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
