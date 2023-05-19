package msu.cmc.jaweb.dao.impl;

import msu.cmc.jaweb.dao.RentalDao;
import msu.cmc.jaweb.models.Rental;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class RentalDaoImpl extends CommonDaoImpl<Rental, Long> implements RentalDao {

    public RentalDaoImpl() {
        super(Rental.class);
    }

    @Override
    public List<Rental> getAllRentalByPeriod(Timestamp from, Timestamp to) {
        try (Session session = sessionFactory.openSession()) {
            if (to == null && from == null) {
                return (List<Rental>) getAll();
            } else if (to == null) {
                Query<Rental> query = session.createQuery("FROM Rental WHERE start_time >= :queryFrom", Rental.class)
                        .setParameter("queryFrom", from);
                return query.getResultList().size() == 0 ? null : query.getResultList();
            } else if (from == null) {
                Query<Rental> query = session.createQuery("FROM Rental WHERE start_time <= :queryTo", Rental.class)
                        .setParameter("queryTo", to);
                return query.getResultList().size() == 0 ? null : query.getResultList();
            } else {
                Query<Rental> query = session.createQuery("FROM Rental WHERE start_time BETWEEN :queryFrom AND :queryTo", Rental.class)
                        .setParameter("queryFrom", from)
                        .setParameter("queryTo", to);
                return query.getResultList().size() == 0 ? null : query.getResultList();
            }
        }
    }
}
