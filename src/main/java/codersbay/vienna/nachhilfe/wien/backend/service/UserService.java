package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    public void createTeacher(Teacher teacher) {
        userRepository.save(teacher);
    }

    public Optional<User> findById(Long studentId) {
        return userRepository.findById(studentId);
    }

    public void connectProfileWithUser(Long userId, Long profileId) {
        User student = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Student Id not found"));
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new IllegalArgumentException("Profile Id not found"));

        student.setProfile(profile);

        userRepository.save(student);
    }





}
