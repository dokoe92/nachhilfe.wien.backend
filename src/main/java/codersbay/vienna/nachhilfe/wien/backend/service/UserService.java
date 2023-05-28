package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.DTO.TeacherDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.TeacherMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final TeacherMapper teacherMapper;

    public Teacher createTeacherWithProfile(Teacher teacher) {
        Profile profile = teacher.getProfile();
        profileRepository.save(profile);
        teacher.setProfile(profile);
        userRepository.save(teacher);
        return teacher;
    }


    public void createStudent(Student student) {
        userRepository.save(student);
    }


    public Optional<User> findById(Long studentId) {
        return userRepository.findById(studentId);
    }




}
