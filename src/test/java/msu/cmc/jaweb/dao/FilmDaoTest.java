package msu.cmc.jaweb.dao;

import msu.cmc.jaweb.models.Film;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class FilmDaoTest {

    @Autowired
    private FilmDao filmDao;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    void addFilms() {
        List<Film> films = new ArrayList<>();
        films.add(new Film("Закованная фильмой", "неизвестно", "Нептун", "Никанор Туркин", 1918L));
        films.add(new Film(3000L, "Трон", "научная фантастика", "Walt Disney Pictures", "Стивен Лисбергер", 1982L, 149L, 69L));
        films.add(new Film(null, "Звёздные войны: Эпизод 4 - Новая надежда", "фантастика",
                "Lucasfilm", "Джордж Лукас", 1977L, 249L, 99L));
        films.add(new Film(null, "Звёздные войны: Эпизод 5 - Империя наносит ответный удар", "фантастика",
                "Lucasfilm", "Джордж Лукас", 1981L, 249L, 99L));
        films.add(new Film(null, "Звёздные войны: Эпизод 6 - Возвращение джедая", "фантастика",
                "Lucasfilm", "Джордж Лукас", 1983L, 249L, 99L));
        films.add(new Film(null, "Аватар", "фантастика", "20th Century Fox", "Джеймс Кэмерон", 2009L, 199L, 79L));
        films.add(new Film("Аватар: Путь воды", "фантастика", "Lightstorm Entertainment", "Джеймс Кэмерон", 2022L));

        filmDao.saveCollection(films);
    }

    @AfterEach
    void eraseFilms() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE film RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testId() {
        Film film = filmDao.getById(1L);
        assertNotNull(film);
        assertEquals(1L, film.getId());
    }

    @Test
    void testYear() {
        List<Film> films = filmDao.getAllFilmByYear(0L, 1983L);
        assertEquals(5, films.size());

        for (Film film : films) {
            assertTrue(film.getRelease_year() >= 0L &&
                       film.getRelease_year() <= 1983L);
        }
    }

    @Test
    void testTitle() {
        List<Film> films = filmDao.getAllFilmByTitle("Звёздные войны");
        assertEquals(3, films.size());

        for (Film film : films) {
            assertTrue(film.getTitle().contains("Звёздные войны"));
        }
    }

    @Test
    void testCompany() {
        List<Film> films = filmDao.getAllFilmByCompany("Disney");
        assertEquals(1, films.size());
        assertEquals("Walt Disney Pictures", films.get(0).getCompany());
    }

    @Test
    void testDirector() {
        List<Film> films = filmDao.getAllFilmByDirector("Кэмерон");
        assertEquals(2, films.size());

        for (Film film : films) {
            assertEquals("Джеймс Кэмерон", film.getDirector());
        }
    }

    @Test
    void testGenre() {
        List<Film> films = filmDao.getAllFilmByGenre("фантастика");
        assertEquals(6, films.size());

        for (Film film : films) {
            assertTrue(film.getGenre().contains("фантастика"));
        }
    }

    @Test
    void testRentPrice() {
        List<Film> films = filmDao.getAllFilmByRentPrice(0L, 80L);
        assertEquals(2, films.size());

        for (Film film : films) {
            assertTrue(film.getRent_price() >= 0L &&
                       film.getRent_price() <= 80L);
        }
    }

    @Test
    void testPurchasePrice() {
        List<Film> films = filmDao.getAllFilmByPurchasePrice(120L, 1000L);
        assertEquals(5, films.size());

        for (Film film : films) {
            assertTrue(film.getPurchase_price() >= 120L &&
                       film.getPurchase_price() <= 1000L);
        }
    }
}
