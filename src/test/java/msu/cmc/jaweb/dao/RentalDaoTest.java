package msu.cmc.jaweb.dao;

import msu.cmc.jaweb.models.Client;
import msu.cmc.jaweb.models.Film;
import msu.cmc.jaweb.models.Rental;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
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
        clientList.add(new Client(null, "Гэри Джон Браннан", "garybrannan@gmail.com", "+44718979983"));
        clientList.add(new Client(null, "Марта Элизабет Браннан", "martha121212@hotmail.com", "+44818767009"));
        clientDao.saveCollection(clientList);

        List<Film> filmList = new ArrayList<>();
        filmList.add(new Film(null, "Звёздные войны: Эпизод 4 - Новая надежда", "фантастика",
                "Lucasfilm", "Джордж Лукас", 1977L, 249L, 99L));
        filmList.add(new Film(null, "Звёздные войны: Эпизод 5 - Империя наносит ответный удар", "фантастика",
                "Lucasfilm", "Джордж Лукас", 1981L, 249L, 99L));
        filmList.add(new Film(null, "Звёздные войны: Эпизод 6 - Возвращение джедая", "фантастика",
                "Lucasfilm", "Джордж Лукас", 1983L, 249L, 99L));
        filmDao.saveCollection(filmList);

        List<Rental> rentalList = new ArrayList<>();
        rentalList.add(new Rental(333L, filmList.get(0), clientList.get(0), RENT,
                Timestamp.valueOf("2016-11-01 12:00:00"),
                Timestamp.valueOf("2016-11-02 12:00:00"),
                99L));
        rentalList.add(new Rental(null, filmList.get(0), clientList.get(1), RENT,
                Timestamp.valueOf("2016-11-01 12:03:11"),
                Timestamp.valueOf("2016-11-02 12:03:11"),
                99L));
        rentalList.add(new Rental(filmList.get(0), clientList.get(0), PURCHASE,
                Timestamp.valueOf("2016-11-02 14:17:51"),
                249L));
        rentalList.add(new Rental(filmList.get(1), clientList.get(0), PURCHASE,
                Timestamp.valueOf("2016-11-02 14:20:03"),
                249L));
        rentalList.add(new Rental(filmList.get(2), clientList.get(0), PURCHASE,
                Timestamp.valueOf("2016-11-02 14:22:59"),
                249L));
        rentalDao.saveCollection(rentalList);
    }

    @Test
    void testId() {
        Rental rental = rentalDao.getById(1L);
        assertEquals(1L, rental.getId());
        assertEquals("Звёздные войны: Эпизод 4 - Новая надежда", rental.getFilm().getTitle());
        assertEquals("Гэри Джон Браннан", rental.getClient().getFull_name());
        assertEquals(RENT, rental.getRent_or_purchase());
        assertEquals(Timestamp.valueOf("2016-11-01 12:00:00"), rental.getStart_time());
        assertEquals(Timestamp.valueOf("2016-11-02 12:00:00"), rental.getEnd_time());
        assertEquals(99L, rental.getPrice());
    }

    @Test
    void testPeriod() {
        Timestamp ts1 = Timestamp.valueOf("2016-11-01 00:00:00");
        Timestamp ts2 = Timestamp.valueOf("2016-11-01 23:59:59");

        List<Rental> rentals = rentalDao.getAllRentalByPeriod(ts1, ts2);
        assertEquals(2, rentals.size());

        for (Rental rental : rentals) {
            assertTrue(
                ts1.compareTo(rental.getStart_time()) <= 0 &&
                ts2.compareTo(rental.getStart_time()) >= 0
            );
        }
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
