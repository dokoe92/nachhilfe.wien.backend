package codersbay.vienna.nachhilfe.wien.backend.rest;

import codersbay.vienna.nachhilfe.wien.backend.Application;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("junit")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(classes = {Application.class})
public abstract class AbstractControllerTest {

    @PersistenceContext
    protected EntityManager entityManager;

    protected void flush() {
        this.entityManager.flush();
    }
}
