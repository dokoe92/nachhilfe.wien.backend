package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public void createProfile(Profile profile) {
        profileRepository.save(profile);
    }

    public void createStudent(Student student) {
        userRepository.save(student);
    }



}
