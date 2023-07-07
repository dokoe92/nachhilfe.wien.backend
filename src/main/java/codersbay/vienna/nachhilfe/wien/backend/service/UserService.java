package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.ProfileDTO;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


    public Optional<User> findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user;
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Transactional
    public void updateProfilePicture(Long userId, String imageBase64) {
        try {
            User user = findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId +" not found."));
            user.getProfile().setImageBase64(imageBase64);
            profileRepository.save(user.getProfile());
        } catch ( RuntimeException e) {
            throw new IllegalArgumentException("Couldn't set profile picture");
        }
    }

}
