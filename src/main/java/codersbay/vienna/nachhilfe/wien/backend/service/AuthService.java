package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.AuthResponse;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ProfileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;

    public AuthResponse findByEmailAndPassword(String email, String password) {
        Profile profile = profileRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ProfileNotFoundException("No profile found with this email and passowrd"));
        User user = profile.getUser();
        AuthResponse authResponse = new AuthResponse();

        authResponse.setId(profile.getId());
        authResponse.setAccessToken("1234");
        authResponse.setNewMessage(false);
        authResponse.setEmail(profile.getEmail());
        authResponse.setFirstName(user.getFirstName());
        authResponse.setLastName(user.getLastName());
        authResponse.setType(user.getUserType());

        return authResponse;
    }
}
