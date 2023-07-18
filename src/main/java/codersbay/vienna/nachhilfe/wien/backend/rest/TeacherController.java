package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherPublicDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.teachermapper.TeacherPublicMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDistricts;
import codersbay.vienna.nachhilfe.wien.backend.model.updaterequest.TeacherUpdateRequest;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotAuthorizedException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.searchobjects.TeacherSearchObject;
import codersbay.vienna.nachhilfe.wien.backend.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
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
    private final TeacherPublicMapper teacherPublicMapper;
    private final JwtService jwtService;

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
    @PutMapping("/update-districts/{teacherId}")
    @Operation(
            description = "Update the districts of a teacher via teacherId - the new array will replace all old districts"
    )
    public ResponseEntity<TeacherDistricts> updateTeacherDistricts(@RequestBody TeacherDistricts districts, @PathVariable Long teacherId, HttpServletRequest request){
        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);

        if (!teacherId.equals(userId)) {
            throw new UserNotAuthorizedException("User not authorized!");
        }

        TeacherDistricts teacherDistricts = teacherService.updateTeacherDistricts(districts, teacherId);
        return new ResponseEntity<>(teacherDistricts, HttpStatus.OK);
    }

    @GetMapping("/teacher-profiles")
    @Operation(
            description = "Get all public teacher profiles"
    )
    public ResponseEntity<List<TeacherPublicDTO>> getAllPublicTeacherProfiles() {
        List<Teacher> teachersPublic = teacherService.getAllTeachersPublic();
        List<TeacherPublicDTO> teachersPublicDTO = teachersPublic.stream()
                .map(teacherPublicMapper::toDTO)
                .toList();
        return new ResponseEntity<>(teachersPublicDTO, HttpStatus.OK);
    }

    @GetMapping("/find-teacher/{teacherId}")
    @Operation(
            description = "Find a teacher by his id"
    )
    public ResponseEntity<TeacherPublicDTO> findTeacherById(@PathVariable Long teacherId) {
        Teacher teacher = teacherService.findTeacherById(teacherId);
        TeacherPublicDTO teacherPublicDTO = teacherPublicMapper.toDTO(teacher);
        return new ResponseEntity<>(teacherPublicDTO, HttpStatus.OK);
    }

    @PutMapping("/updateTeacher/{teacherId}")
    public ResponseEntity<TeacherPublicDTO> updateTeacher(
            @PathVariable Long teacherId,
            @RequestBody TeacherUpdateRequest request
    ) {


        Teacher updatedTeacher =
                teacherService.updateTeacher(teacherId,
                        request.getFirstName(),
                        request.getLastName(),
                        request.getDescription(),
                        request.getPassword(),
                        request.isActive());
        TeacherPublicDTO dto = teacherPublicMapper.toDTO(updatedTeacher);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }


    @DeleteMapping("/deleteTeacher/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long teacherId, HttpServletRequest request) {
        String token = jwtService.getTokenFromHeader(request.getHeader("Authorization"));
        Long userId = jwtService.extractUserId(token);

        if (!teacherId.equals(userId)) {
            throw new UserNotAuthorizedException("User not authorized!");
        }

        boolean deleted = teacherService.deleteAdmin(teacherId);
        if (deleted) {
            return ResponseEntity.ok("Teacher with ID " + teacherId + " deleted succesfully.");
        } else {
            throw new UserNotFoundException("Teacher with ID " + teacherId + " was not found.");
        }
    }

    @PostMapping("/filter-teachers")
    public ResponseEntity<List<TeacherPublicDTO>> filterTeachers(@RequestBody TeacherSearchObject so) {
        List<TeacherPublicDTO> teachersFiltered = teacherService.filterTeacher(so).stream()
                .map(teacherPublicMapper::toDTO).toList();
        return new ResponseEntity<>(teachersFiltered, HttpStatus.OK);

    }
}
