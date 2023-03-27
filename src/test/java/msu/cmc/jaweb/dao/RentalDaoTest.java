package msu.cmc.jaweb.dao;

import msu.cmc.jaweb.models.Client;
import msu.cmc.jaweb.models.Rental;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.postgresql.util.PGmoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import static msu.cmc.jaweb.models.Rental.RentalMethod.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class RentalDaoTest {

    @Autowired
    private ClientDao clientDao;
    @Autowired
    private FilmDao filmDao;
    @Autowired
    private RentalDao rentalDao;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    void addRentals() {
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client(1000L, "Гэри Джон Браннан", "garybrannan@gmail.com", "+44718979983"));
        clientList.add(new Client(1001L, "Марта Элизабет Браннан", "martha121212@hotmail.com", "+44818767009"));
        clientList.add(new Client(null, "Кристофер Френк Карандини Ли", "christopherlee@yahoo.com", "+44746551910"));
        clientList.add(new Client(128L, "Чарльз Спенсер Чаплин", "chaplin@chaplin.net", null));
        clientList.add(new Client("Айседора Дункан", "i.duncan@gmail.com"));
        clientList.add(new Client(null, "Спенсер Джонс", "spenser0jones@gmail.com", "+17338901991"));
        clientDao.saveCollection(clientList);

        // TODO: add films and rentals
    }

    @AfterEach
    void eraseRentals() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE rental RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
