package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.dto.userdto.ProfileDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.ProfileMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerAuth")
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final ProfileMapper profileMapper;

    @GetMapping("/{id}")
    @Operation(
            summary = "Will be replaced most likely by an admin method - use teacher and student methods instead"
    )
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

@PostMapping("/picture/{id}")
    @Operation(
            summary = "Update a user's profile picture"
    )
    public ResponseEntity<ProfileDTO> setProfilePicture(@PathVariable Long id, @RequestBody String imageBase64) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            user.get().getProfile().setImageBase64(imageBase64);
            return new ResponseEntity<>(profileMapper.toDTO(user.get().getProfile()), HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found");
        }

}






}