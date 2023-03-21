package msu.cmc.jaweb.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import msu.cmc.jaweb.models.Client;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class ClientDaoTest {
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    void addClients() {
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client(1000L, "Гэри Джон Браннан", "garybrannan@gmail.com", "+44718979983"));
        clientList.add(new Client(1001L, "Марта Элизабет Браннан", "martha121212@hotmail.com", "+44818767009"));
        clientList.add(new Client(null, "Кристофер Френк Карандини Ли", "christopherlee@yahoo.com", "+44746551910"));
        clientList.add(new Client(128L, "Чарльз Спенсер Чаплин", "chaplin@chaplin.net", null));
        clientList.add(new Client(null, "Айседора Дункан", "i.duncan@gmail.com", null));
        clientList.add(new Client(null, "Спенсер Джонс", "spenser0jones@gmail.com", "+17338901991"));

        clientDao.saveCollection(clientList);
    }

    @AfterEach
    void destroy() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE client RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void test1() {
        List<Client> clientList = (List<Client>) clientDao.getAll();
        assertEquals(6, clientList.size());
    }
}
