package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Admin;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Student;
import codersbay.vienna.nachhilfe.wien.backend.model.Entity.User;
import codersbay.vienna.nachhilfe.wien.backend.model.updaterequest.AdminUpdateRequest;
import codersbay.vienna.nachhilfe.wien.backend.model.updaterequest.StudentUpdateRequest;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Admin>> findAllAdmins(){
        List<Admin> adminList = adminService.findAllAdmins();
        return new ResponseEntity<>(adminList, HttpStatus.OK);
    }

    @PutMapping("/updateAdmin/{adminId}")
    public ResponseEntity<Admin> updateAdmin(
            @PathVariable Long adminId,
            @RequestBody AdminUpdateRequest request
    ) {
        Admin updatedAdmin =
                adminService.updateAdmin (adminId,
                        request.getFirstName(),
                        request.getLastName(),
                        request.getDescription());
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }
}






