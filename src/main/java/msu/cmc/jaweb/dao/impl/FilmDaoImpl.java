package msu.cmc.jaweb.dao.impl;

import msu.cmc.jaweb.dao.FilmDao;
import msu.cmc.jaweb.models.Film;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmDaoImpl extends CommonDaoImpl<Film, Long> implements FilmDao {

    public FilmDaoImpl() {
        super(Film.class);
    }

    @Override
    public List<Film> getAllFilmByTitle(String filmTitle) {
        try (Session session = sessionFactory.openSession()) {
            Query<Film> query = session.createQuery("FROM Film WHERE title LIKE :filmTitle", Film.class)
                    .setParameter("filmTitle", likeTemplate(filmTitle));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Film> getAllFilmByYear(Long from, Long to) {
        try (Session session = sessionFactory.openSession()) {
            Query<Film> query = session.createQuery("FROM Film WHERE release_year BETWEEN :from AND :to", Film.class)
                    .setParameter("from", from)
                    .setParameter("to", to);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Film> getAllFilmByGenre(String genre) {
        try (Session session = sessionFactory.openSession()) {
            Query<Film> query = session.createQuery("FROM Film WHERE genre LIKE :queryGenre", Film.class)
                    .setParameter("queryGenre", likeTemplate(genre));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Film> getAllFilmByCompany(String company) {
        try (Session session = sessionFactory.openSession()) {
            Query<Film> query = session.createQuery("FROM Film WHERE company LIKE :queryCompany", Film.class)
                    .setParameter("queryCompany", likeTemplate(company));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Film> getAllFilmByDirector(String director) {
        try (Session session = sessionFactory.openSession()) {
            Query<Film> query = session.createQuery("FROM Film WHERE director LIKE :queryDirector", Film.class)
                    .setParameter("queryDirector", likeTemplate(director));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Film> getAllFilmByRentPrice(Long from, Long to) {
        try (Session session = sessionFactory.openSession()) {
            Query<Film> query = session.createQuery("FROM Film WHERE rent_price BETWEEN :from AND :to", Film.class)
                    .setParameter("from", from)
                    .setParameter("to", to);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Film> getAllFilmByPurchasePrice(Long from, Long to) {
        try (Session session = sessionFactory.openSession()) {
            Query<Film> query = session.createQuery("FROM Film WHERE purchase_price BETWEEN :from AND :to", Film.class)
                    .setParameter("from", from)
                    .setParameter("to", to);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
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
