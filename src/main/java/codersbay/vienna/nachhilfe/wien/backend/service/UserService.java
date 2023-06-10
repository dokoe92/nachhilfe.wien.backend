package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.PropertyPermission;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


    public Teacher createTeacherWithProfile(Teacher teacher) {
        Profile profile = teacher.getProfile();
        profileRepository.save(profile);
        teacher.setProfile(profile);
        userRepository.save(teacher);
        return teacher;
    }

    public Student createStudentWithProfile(Student student) {
        Profile profile = student.getProfile();
        profileRepository.save(profile);
        student.setProfile(profile);
        userRepository.save(student);
        return student;
    }

    //finding a user by ID
    public Optional<User> findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user;
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    //checking if a user is existing by ID
    public boolean existsById(Long userId) {

        return userRepository.existsById(userId);
    }

    //create an Admin with Profile
    public Admin createAdminWithProfile(Admin admin) {
        Profile profile = admin.getProfile();
        profileRepository.save(profile);
        admin.setProfile(profile);
        userRepository.save(admin);
        return admin;

    }





    // updateTeacher, updateStudent, updateAdmin
    //deleteUserById, deleteUser

}
