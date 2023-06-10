package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Admin;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import codersbay.vienna.nachhilfe.wien.backend.respository.AdminRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> findById(Long adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        if(admin.isPresent()){
            return admin;
        } else {
            throw new UserNotFoundException("Admin not found");
        }
    }

    public Admin updateAdmin(Long adminId, String firstName, String lastName, String description) {
        Optional<Admin> admin  = adminRepository.findById(adminId);
        if (admin.isPresent()) {
            Admin existingAdmin = admin.get();

            User user = existingAdmin.getProfile().getUser();
            user.setFirstName(firstName);
            user.setLastName(lastName);

            Profile profile = existingAdmin.getProfile();

            //Update the properties of the existing teacher with the updated values
            profile.setActive(profile.isActive());
            profile.setDescription(description);
            profile.setPassword(profile.getPassword());
            profile.setEmail(profile.getEmail());

            adminRepository.save(existingAdmin);

            return existingAdmin;
        } else {
            throw new UserNotFoundException("Admin not found");
        }
    }
}
