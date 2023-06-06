package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.model.TeacherDistricts;
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

    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

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



}
