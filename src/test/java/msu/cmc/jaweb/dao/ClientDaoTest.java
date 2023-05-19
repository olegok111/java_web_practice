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
        clientList.add(new Client("Айседора Дункан", "i.duncan@gmail.com"));
        clientList.add(new Client(null, "Спенсер Джонс", "spenser0jones@gmail.com", "+17338901991"));
        clientDao.saveCollection(clientList);
    }

    @AfterEach
    void eraseClients() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE client RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testGetAll() {
        List<Client> clientList = (List<Client>) clientDao.getAll();
        assertEquals(6, clientList.size());
    }

    @Test
    void testGetMultiple() {
        List<Client> clientList = clientDao.getAllClientByName("Спенсер");
        assertEquals(2, clientList.size());
    }

    @Test
    void testId1() {
        Client client = clientDao.getById(1L);
        assertEquals(1, client.getId());
    }

    @Test
    void testTooBigId() {
        Client client = clientDao.getById(1000L);
        assertNull(client);
    }

    @Test
    void testEmail() {
        List<Client> clientList = clientDao.getAllClientByName("Чаплин");
        assertEquals("chaplin@chaplin.net", clientList.get(0).getEmail());
    }

    @Test
    void testPhone() {
        List<Client> clientList = clientDao.getAllClientByName("Спенсер Джонс");
        assertEquals("+17338901991", clientList.get(0).getPhone());
    }

    @Test
    void testUpdate() {
        Client client = clientDao.getById(1L);
        client.setPhone("+78005553535");
        clientDao.update(client);
        assertEquals("+78005553535", clientDao.getById(1L).getPhone());
    }

    @Test
    void testDelete() {
        List<Client> clients = clientDao.getAllClientByName("Айседора Дункан");
        clientDao.delete(clients.get(0));
        assertNull(clientDao.getAllClientByName("Айседора Дункан"));
    }

    @Test
    void testDeleteById() {
        clientDao.deleteById(3L);
        assertNull(clientDao.getById(3L));
    }
}
