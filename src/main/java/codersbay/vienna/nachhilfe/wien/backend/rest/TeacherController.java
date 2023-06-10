package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.Pojo.TeacherDistricts;
import codersbay.vienna.nachhilfe.wien.backend.model.updaterequest.TeacherUpdateRequest;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<Teacher>> findAllTeachers() {
        List<Teacher> teacherList = teacherService.findAllTeachers();
        return new ResponseEntity<>(teacherList, HttpStatus.OK);
    }

    /**
     * Updates the districts of a teacher.
     * All districts will be deleted from user and the actual set from the TeacherDistricts object will be saved.
     *
     * @param districts the TeacherDistricts object containing the updated districts
     * @param teacherId the ID of the teacher
     * @return ResponseEntity with the updated TeacherDistricts object and HTTP status OK
     */
    @PutMapping("/updateDistricts/{teacherId}")
    public ResponseEntity<TeacherDistricts> updateTeacherDistricts(@RequestBody TeacherDistricts districts, @PathVariable Long teacherId) {
        TeacherDistricts teacherDistricts = teacherService.updateTeacherDistricts(districts, teacherId);
        return new ResponseEntity<>(teacherDistricts, HttpStatus.OK);
    }

    @PutMapping("/updateTeacher/{teacherId}")
    public ResponseEntity<Teacher> updateTeacher(
            @PathVariable Long teacherId,
            @RequestBody TeacherUpdateRequest request
    ) {
        Teacher updatedTeacher =
                teacherService.updateTeacher(teacherId,
                        request.getFirstName(),
                        request.getLastName(),
                        request.getDescription());
        return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);

    }


    @DeleteMapping("/deleteTeacher/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long teacherId) {
        boolean deleted = teacherService.deleteAdmin(teacherId);
        if (deleted) {
            return ResponseEntity.ok("Teacher with ID " + teacherId + " deleted succesfully.");
        } else {
            throw new UserNotFoundException("Teacher with ID " + teacherId + " was not found.");
        }
    }
}
