package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.AdminRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.MissingIdException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.searchobjects.UserSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> findById(Long adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        if (admin.isPresent()) {
            return admin;
        } else {
            throw new UserNotFoundException("Admin not found");
        }
    }

    public Admin updateAdmin(Long adminId, String firstName, String lastName, String description, String email, String password, boolean active ) {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if (adminOptional.isPresent()) {
            Admin existingAdmin = adminOptional.get();

            User user = existingAdmin.getProfile().getUser();

            if (firstName != null) {
                user.setFirstName(firstName);
            }
            if (lastName != null) {
                user.setLastName(lastName);
            }

            Profile profile = existingAdmin.getProfile();

            //Update the properties of the existing teacher with the updated values
            if (active) {
                profile.setActive(true);
            }

            if (description != null) {
                profile.setDescription(description);
            }

            if (password != null) {
                profile.setPassword(password);
            }

            adminRepository.save(existingAdmin);

            return existingAdmin;
        } else {
            throw new UserNotFoundException("Admin not found");
        }
    }

    public User findUser(UserSearch search) {
        if (search.getId() != null && search.getEmail() == null) {
            User user = userRepository.findById(search.getId())
                    .orElseThrow(() ->  new ResourceNotFoundException("User not found!"));
            if (user instanceof Admin) {
                throw new UserNotAuthorizedException("Admins not authorized to edit other admins!");
            } else {
                return user;
            }
        }
        if (search.getEmail() != null && search.getId() == null) {
            User user = userRepository.findByEmail(search.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
            if (user instanceof Admin) {
                throw new UserNotAuthorizedException("Admins not authorized to edit other admins!");
            } else {
                return user;
            }
        }
        throw new MissingIdException("Please search for id or email!");
    }

    public boolean deleteAdmin(Long adminId) {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if (adminOptional.isPresent()) {
            adminRepository.delete(adminOptional.get());
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteStudent(Long studentId){
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isPresent()){
            User student = studentOptional.get();
            if(student instanceof Student){
                userRepository.delete(student);
                return true;
            }
        }
        return false;
    }

    public boolean deleteTeacher(Long teacherId){
        Optional<User> teacherOptional = userRepository.findById(teacherId);
        if (teacherOptional.isPresent()){
            User teacher = teacherOptional.get();
            if(teacher instanceof Teacher){
                userRepository.delete(teacher);
                return true;
            }
        }
        return false;
    }


}
