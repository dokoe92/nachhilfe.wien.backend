package codersbay.vienna.nachhilfe.wien.backend.repo;

import codersbay.vienna.nachhilfe.wien.backend.model.Coaching;
import codersbay.vienna.nachhilfe.wien.backend.model.Districts;
import codersbay.vienna.nachhilfe.wien.backend.model.Profile;
import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import codersbay.vienna.nachhilfe.wien.backend.model.Teacher;
import codersbay.vienna.nachhilfe.wien.backend.respository.CoachingRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.searchobjects.TeacherSearchObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testSearchBy() {
        // given
        final Teacher t = new Teacher();
        final Profile p = new Profile();
        final Coaching c = new Coaching();
        p.setEmail("1234");
        p.setPassword("1234");
        profileRepository.save(p);
        t.setProfile(p);
        t.setDistricts(Set.of(Districts.DISTRICT_1010, Districts.DISTRICT_1030, Districts.DISTRICT_1040));
        c.setUser(t);
        c.setSubject(Subject.DEUTSCH);
        c.setRate(12.00);
        p.setActive(true);


        final Coaching persistedCoaching = coachingRepository.save(c);
        assertNotNull(persistedCoaching);
        t.setCoachings(Set.of(persistedCoaching));

        final Teacher persistedTeacher = teacherRepository.save(t);
        assertNotNull(persistedTeacher);

        List<Districts> districts = List.of(Districts.DISTRICT_1040, Districts.DISTRICT_1050);
        Subject subject = Subject.DEUTSCH;
        Long min = 5L;
        Long max = 12L;

        // search by empty search object
        final TeacherSearchObject tso = new TeacherSearchObject();
        List<Teacher> teachers = teacherRepository.filterTeachers(tso);
        assertFalse(teachers.isEmpty());

        // search by districts - success
        tso.setDistricts(districts);
        teachers = teacherRepository.filterTeachers(tso);
        assertFalse(teachers.isEmpty());

        // search by districts - fail
        tso.setDistricts(List.of(Districts.DISTRICT_1100));
        teachers = teacherRepository.filterTeachers(tso);
        assertTrue(teachers.isEmpty());

        // search by district and subject
        tso.setDistricts(districts);
        tso.setSubject(subject);
        teachers = teacherRepository.filterTeachers(tso);
        assertFalse(teachers.isEmpty());

        // search by district and subject - fail
        tso.setSubject(Subject.ENGLISCH);
        teachers = teacherRepository.filterTeachers(tso);
        assertTrue(teachers.isEmpty());

        // search by min and max rate
        tso.setSubject(subject);
        tso.setMinRate(min);
        tso.setMaxRate(max);
        teachers = teacherRepository.filterTeachers(tso);
        assertFalse(teachers.isEmpty());

        // search by min and max rate - fail
        tso.setMinRate((min + 7));
        tso.setMaxRate(max);
        teachers = teacherRepository.filterTeachers(tso);
        assertFalse(teachers.isEmpty());

        // search by min and max rate - fail
        tso.setMinRate((min));
        tso.setMaxRate((max - 1));
        teachers = teacherRepository.filterTeachers(tso);
        assertTrue(teachers.isEmpty());
    }

    @Test
    public void testOnlyActiveTeachersReturned() {
        // given
        final Teacher t = new Teacher();
        final Profile p = new Profile();
        final Coaching c = new Coaching();
        p.setEmail("1234");
        p.setPassword("1234");
        p.setActive(true);
        profileRepository.save(p);
        t.setProfile(p);
        t.setDistricts(Set.of(Districts.DISTRICT_1010, Districts.DISTRICT_1030, Districts.DISTRICT_1040));
        c.setUser(t);
        c.setSubject(Subject.DEUTSCH);
        c.setRate(12.00);
        coachingRepository.save(c);
        t.setCoachings(Set.of(c));
        teacherRepository.save(t);

        final Teacher t2 = new Teacher();
        final Profile p2 = new Profile();
        final Coaching c2 = new Coaching();
        p2.setEmail("test2@gmail.com");
        p2.setPassword("password");
        p2.setActive(false);
        profileRepository.save(p2);
        t2.setProfile(p2);
        t2.setDistricts(Set.of(Districts.DISTRICT_1030, Districts.DISTRICT_1040));
        c2.setUser(t2);
        c2.setSubject(Subject.DEUTSCH);
        c2.setRate(12.00);
        coachingRepository.save(c2);
        t2.setCoachings(Set.of(c2));
        teacherRepository.save(t2);

        // search by empty search object
        final TeacherSearchObject tso = new TeacherSearchObject();
        List<Teacher> teachers = teacherRepository.filterTeachers(tso);

        // The teachers list should not be empty because there is one active teacher
        assertFalse(teachers.isEmpty());
        assertEquals(1, teachers.size()); // There should be only one teacher because only one teacher is active
        assertTrue(teachers.get(0).getProfile().isActive()); // The returned teacher should be active

        // search by subject
        tso.setSubject(Subject.DEUTSCH);
        teachers = teacherRepository.filterTeachers(tso);
        assertFalse(teachers.isEmpty());
        assertEquals(1, teachers.size()); // There should still be only one teacher, because the inactive teacher should not be returned even if the subject matches
        assertTrue(teachers.get(0).getProfile().isActive()); // The returned teacher should be active
    }
}
