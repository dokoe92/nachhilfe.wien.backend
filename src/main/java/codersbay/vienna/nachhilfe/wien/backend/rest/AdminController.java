package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Admin;
import codersbay.vienna.nachhilfe.wien.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
