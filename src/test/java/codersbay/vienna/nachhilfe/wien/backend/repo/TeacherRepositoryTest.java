package codersbay.vienna.nachhilfe.wien.backend.repo;

import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("junit")
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CoachingRepository coachingRepository;

    @Test
    public void testSearchBy(){
        // given
        final Teacher t = new Teacher();
        final Profile p = new Profile();
        final Coaching c = new Coaching();
        p.setEmail("1234");
        p.setPassword("1234");
        profileRepository.save(p);
        t.setProfile(p);
        t.setDistricts(Set.of(Districts.DISTRICT_1010));
        c.setUser(t);
        c.setSubject(Subject.DEUTSCH);
        c.setRate(12.00);

        final Coaching persistedCoaching = coachingRepository.save(c);
        assertNotNull(persistedCoaching);
        t.setCoachings(Set.of(persistedCoaching));

        final Teacher persistedTeacher = teacherRepository.save(t);
        assertNotNull(persistedTeacher);

        List<Districts> districts = List.of(Districts.DISTRICT_1010);
        Subject subject = Subject.DEUTSCH;
        long min = 5;
        long max = 10;

        // when
        teacherRepository.filterTeachers(subject, min, max);
    }
}
