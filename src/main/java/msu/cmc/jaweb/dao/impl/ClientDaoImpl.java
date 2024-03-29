package msu.cmc.jaweb.dao.impl;

import msu.cmc.jaweb.dao.ClientDao;
import msu.cmc.jaweb.models.Client;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDaoImpl extends CommonDaoImpl<Client, Long> implements ClientDao {

    public ClientDaoImpl() {
        super(Client.class);
    }

    @Override
    public List<Client> getAllClientByName(String clientName) {
        if (clientName == null || clientName.isBlank()) {
            return (List<Client>) getAll();
        }

        try (Session session = sessionFactory.openSession()) {
            Query<Client> query = session.createQuery("FROM Client WHERE full_name LIKE :queryName", Client.class)
                    .setParameter("queryName", likeTemplate(clientName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    private String likeTemplate(String s) {
        return "%" + s + "%";
    }
}
