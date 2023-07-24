package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.admindto.UserDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.usermapper.UserMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Admin;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.model.updaterequest.AdminUpdateRequest;
import codersbay.vienna.nachhilfe.wien.backend.model.updaterequest.UserUpdateRequest;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.searchobjects.UserSearch;
import codersbay.vienna.nachhilfe.wien.backend.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Admin>> findAllAdmins() {
        List<Admin> adminList = adminService.findAllAdmins();
        return new ResponseEntity<>(adminList, HttpStatus.OK);
    }

    @PutMapping("/updateAdmin/{adminId}")
    public ResponseEntity<AdminUpdateRequest> updateAdmin(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdateRequest request,
            HttpServletRequest httpServletRequest
    ) {


        String token = jwtService.getTokenFromHeader(httpServletRequest.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);
        if (!userId.equals(adminId)) {
            throw new UserNotAuthorizedException("User not authorized!");
        }

        Admin updatedAdmin =
                adminService.updateAdmin(adminId,
                        request.getFirstName(),
                        request.getLastName(),
                        request.getDescription(),
                        request.getPassword(),
                        request.getEmail(),
                        request.isActive());

        AdminUpdateRequest asDto = new AdminUpdateRequest();
        asDto.setFirstName(updatedAdmin.getFirstName());
        asDto.setLastName(updatedAdmin.getLastName());
        asDto.setDescription(updatedAdmin.getProfile().getDescription());
        asDto.setActive(updatedAdmin.getProfile().isActive());
        asDto.setEmail(updatedAdmin.getProfile().getEmail());

        return new ResponseEntity<>(asDto, HttpStatus.OK);
    }

    @PostMapping("/find-user")
    public ResponseEntity<UserDTO> findUser(@Valid @RequestBody UserSearch userSearch) {
        User user = adminService.findUser(userSearch);
        UserDTO userDTO = userMapper.toDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/edit-user/{userId}")
    public ResponseEntity<UserDTO> editUser (@Valid @RequestBody UserUpdateRequest updateRequest,
                                             @PathVariable Long userId) {
        UserDTO userDTO = userMapper.toDTO(adminService.editUser(updateRequest, userId));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/active-inactive/{userId}")
    public ResponseEntity<Boolean> changeActiveStatus (@PathVariable Long userId,
                                                      @RequestParam Boolean activeStatus) {

        Boolean activeStatusAfterEdit = adminService.editActiveStatus(activeStatus, userId);
        return new ResponseEntity<>(activeStatusAfterEdit, HttpStatus.OK);
    }

    @PutMapping("/delete-image/{userId}")
    public ResponseEntity<Boolean> deleteUserImage (@PathVariable Long userId) {
        Boolean imageDeleted = adminService.deleteImage(userId);
        return new ResponseEntity<>(imageDeleted, HttpStatus.OK);
    }

    @DeleteMapping("/delete-feedback/{feedbackId}")
    public ResponseEntity<Boolean> deleteFeedback(@PathVariable Long feedbackId) {
        Boolean feedbackDeleted = adminService.deleteFeedback(feedbackId);
        return new ResponseEntity<>(feedbackDeleted, HttpStatus.NO_CONTENT);
    }


    //DANGEROUS METHOD - MAYBE DELETE?
    @DeleteMapping("/deleteAdmin/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long adminId) {
        boolean deleted = adminService.deleteAdmin(adminId);
        if (deleted) {
            return ResponseEntity.ok("Admin with ID " + adminId + " deleted succesfully.");
        } else {
            throw new UserNotFoundException("Admin with ID " + adminId + " was not found.");
        }

    }

    @DeleteMapping("/deleteTeacher/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long teacherId) {
        boolean deleted = adminService.deleteTeacher(teacherId);
        if (deleted) {
            return ResponseEntity
                    .ok("Teacher with ID " + teacherId + " deleted successfully.");
        } else {
            throw new UserNotFoundException("Teacher not found with ID " + teacherId);
        }
    }

    @DeleteMapping("/deleteStudent/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        boolean deleted = adminService.deleteStudent(studentId);
        if (deleted) {
            return ResponseEntity
                    .ok("Student with ID " + studentId + " deleted successfully.");
        } else {
            throw new UserNotFoundException("Student not found with ID " + studentId);
        }
    }

    @PutMapping("/delete-user/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long userId, HttpServletRequest request) {
        boolean deleted = adminService.softDeleteUser(userId);
        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long adminId = jwtService.extractUserId(token);
        if (userId.equals(adminId)) {
            throw new IllegalArgumentException ("Admin cannot delete himself!");
        }
        if (deleted) {
            return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(deleted, HttpStatus.NOT_FOUND);
    }
}








