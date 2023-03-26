package msu.cmc.jaweb.dao.impl;

import msu.cmc.jaweb.dao.FilmDao;
import msu.cmc.jaweb.models.Film;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    public List<Film> getAllFilmByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Film> criteriaQuery = builder.createQuery(Film.class);
            Root<Film> root = criteriaQuery.from(Film.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getName() != null) {
                predicates.add(builder.like(root.get("name"), likeTemplate(filter.getName())));
            }

            if (predicates.size() != 0) {
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
            }

            return session.createQuery(criteriaQuery).getResultList();
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
