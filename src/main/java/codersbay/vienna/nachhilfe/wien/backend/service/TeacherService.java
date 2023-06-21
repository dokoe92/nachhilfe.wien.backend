package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDistricts;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private Set<Coaching> coachings;

    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }


    /**
     * Updates the districts of a teacher.
     *
     * @param teacherDistricts  the TeacherDistricts object containing the updated districts
     * @param teacherId         the ID of the teacher
     * @return the updated TeacherDistricts object
     * @throws ResourceNotFoundException if the teacher is not found
     */
    public TeacherDistricts updateTeacherDistricts(TeacherDistricts teacherDistricts, Long teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if (teacher.isEmpty()) {
            throw new ResourceNotFoundException("Teacher with id " + teacherDistricts.getTeacherId() + " not found");
        }
        teacher.get().getDistricts().clear();
        teacher.get().getDistricts().addAll(teacherDistricts.getDistricts());

        teacherRepository.save(teacher.get());

        teacherDistricts.setTeacherId(teacherId);

        return teacherDistricts;
    }

    public List<Teacher> getAllTeachersPublic() {
        return teacherRepository.findAll();
    }

    public Teacher findTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found!"));
    }
    public Teacher updateTeacher(Long teacherId, String firstName, String lastName, String description, String password, String email, boolean active) {
        //find existing teacher by ID
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if (teacher.isPresent()) {
            Teacher existingTeacher = teacher.get();

            User user = existingTeacher.getProfile().getUser();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setCoachings(coachings);

            Profile profile = existingTeacher.getProfile();

            //Update the properties of the existing teacher with the updated values
            profile.setActive(active);
            profile.setDescription(description);
            profile.setPassword(password);
            profile.setEmail(email);

            teacherRepository.save(existingTeacher);

            return existingTeacher;
        } else {
            throw new UserNotFoundException("Teacher not found");
        }
    }


    public boolean deleteAdmin(Long teacherId) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isPresent()){
            teacherRepository.delete(teacherOptional.get());
            return true;
        } else {
            return false;
        }
    }
}
