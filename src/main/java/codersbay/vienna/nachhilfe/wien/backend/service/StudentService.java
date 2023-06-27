package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found!"));
    }

    public Student updateStudent(Long studentId, String firstName, String lastName, String description, String password, String email, boolean active) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student existingStudent = studentOptional.get();

            User user = existingStudent.getProfile().getUser();
            user.setFirstName(firstName);
            user.setLastName(lastName);

            Profile profile = existingStudent.getProfile();

            //Update the properties of the existing teacher with the updated values
            profile.setActive(active);
            profile.setDescription(description);
            profile.setPassword(password);
            profile.setEmail(email);

            studentRepository.save(existingStudent);

            return existingStudent;
        } else {
            throw new UserNotFoundException("Student not found");
        }
    }


    public boolean deleteStudent(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()){
            studentRepository.delete(studentOptional.get());
            return true;
        } else {
            return false;
        }
    }
}
