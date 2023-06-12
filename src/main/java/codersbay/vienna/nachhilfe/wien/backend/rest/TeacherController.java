package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDistricts;
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
     * @param districts    the TeacherDistricts object containing the updated districts
     * @param teacherId    the ID of the teacher
     * @return ResponseEntity with the updated TeacherDistricts object and HTTP status OK
     */
    @PutMapping("/updateDistricts/{teacherId}")
    public ResponseEntity<TeacherDistricts> updateTeacherDistricts(@RequestBody TeacherDistricts districts, @PathVariable Long teacherId){
        TeacherDistricts teacherDistricts = teacherService.updateTeacherDistricts(districts, teacherId);
        return new ResponseEntity<>(teacherDistricts, HttpStatus.OK);
    }
}
