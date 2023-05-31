package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.model.AuthRequest;
import codersbay.vienna.nachhilfe.wien.backend.model.AuthResponse;
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

    @PostMapping
    public ResponseEntity<AuthResponse> getAuthResponse(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.findByEmailAndPassword(authRequest.getEmail(), authRequest.getPassword());
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
