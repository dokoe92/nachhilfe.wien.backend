package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.User;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDistricts;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.UserNotFoundException;
import codersbay.vienna.nachhilfe.wien.backend.searchobjects.TeacherSearchObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

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
    public Teacher updateTeacher(Long teacherId, String firstName, String lastName, String description, String password, boolean active) {
        //find existing teacher by ID
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if (teacher.isPresent()) {
            Teacher existingTeacher = teacher.get();

            User user = existingTeacher.getProfile().getUser();
            if (firstName != null) {
            user.setFirstName(firstName);
            }

            if (lastName != null) {
                user.setLastName(lastName);
            }

            Profile profile = existingTeacher.getProfile();

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

    public List<Teacher> filterTeacher(TeacherSearchObject so) {
        List<Teacher> teachersFiltered = teacherRepository.filterTeachers(so);
        return teachersFiltered;
    }
}
