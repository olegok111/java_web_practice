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
        films.add(new Film(null, "ТРОН", "genre", "company", "director", 1980L, 149L, 69L));
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
        Film film = filmDao.getFilmById(1L);
        assertEquals("Закованная фильмой", film.getTitle());
    }

    @Test
    void testYear() {
        List<Film> films = filmDao.getAllFilmByYear(0L, 1980L);
        assertEquals(2, films.size());
    }
}
