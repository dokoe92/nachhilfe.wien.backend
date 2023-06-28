package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.AppointmentDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.AppointmentMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.Appointment;
import codersbay.vienna.nachhilfe.wien.backend.model.Status;
import codersbay.vienna.nachhilfe.wien.backend.respository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig
@DataJpaTest
@ComponentScan("codersbay.vienna.nachhilfe.wien.backend.mapper.appointmentmapper")
class AppointmentServiceIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testConfirmAppointment() {
        // Create test data
        Appointment appointment = new Appointment();
        appointment.setStatus(Status.SCHEDULED);
        appointment.setConfirmed(false);


        Long appointmentId = appointment.getId();

        // Invoke the method
        AppointmentDTO result = appointmentService.confirmAppointment(appointmentId);

        // Retrieve the persisted appointment from the database
        Appointment persistedAppointment = entityManager.find(Appointment.class, appointmentId);

        // Assert the updated fields in the persisted appointment
        assertNotNull(persistedAppointment);
        assertTrue(persistedAppointment.isConfirmed());
        assertEquals(Status.CONFIRMED, persistedAppointment.getStatus());

        // Assert the result
        assertNotNull(result);
        assertTrue(result.isConfirmed());
        assertEquals(Status.CONFIRMED, result.getStatus());
    }
}