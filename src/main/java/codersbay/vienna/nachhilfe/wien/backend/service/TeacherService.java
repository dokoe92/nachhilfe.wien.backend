package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto.TeacherDistricts;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;


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
        teacher.get().getDisctricts().clear();
        teacher.get().getDisctricts().addAll(teacherDistricts.getDistricts());

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


}
