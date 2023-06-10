package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> findAllStudents() {

        return studentRepository.findAll();
    }

    public Student updateStudent(Long studentId, String firstName, String lastName, String description) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            Student existingStudent = student.get();

            User user = existingStudent.getProfile().getUser();
            user.setFirstName(firstName);
            user.setLastName(lastName);

            Profile profile = existingStudent.getProfile();

            //Update the properties of the existing teacher with the updated values
            profile.setActive(profile.isActive());
            profile.setDescription(description);
            profile.setPassword(profile.getPassword());
            profile.setEmail(profile.getEmail());

            studentRepository.save(existingStudent);

            return existingStudent;
        } else {
            throw new UserNotFoundException("Student not found");
        }
    }


}


