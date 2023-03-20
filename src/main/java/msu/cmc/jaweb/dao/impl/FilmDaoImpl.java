package msu.cmc.jaweb.dao.impl;

import msu.cmc.jaweb.dao.FilmDao;
import msu.cmc.jaweb.models.Film;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class FilmDaoImpl extends CommonDaoImpl<Film, Long> implements FilmDao {

    public FilmDaoImpl() {
        super(Film.class);
    }

    public List<Film> getAllFilmByTitle(String filmTitle) {
        try (Session session = sessionFactory.openSession()) {
            Query<Film> query = session.createQuery("FROM Film WHERE title LIKE :queryTitle", Film.class)
                    .setParameter("queryTitle", likeTemplate(filmTitle));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    public Film getFilmById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Film> query = session.createQuery("FROM Film WHERE id = :queryId", Film.class)
                    .setParameter("queryId", id);
            return query.getResultList().size() == 0 ? null : query.getSingleResult();
        }
    }

    private String likeTemplate(String s) {
        return "%" + s + "%";
    }
}
