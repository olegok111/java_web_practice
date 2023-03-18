package msu.cmc.jaweb.dao.impl;

import msu.cmc.jaweb.dao.ClientDao;
import msu.cmc.jaweb.models.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDaoImpl extends CommonDaoImpl<Client, Long> implements ClientDao {

    public ClientDaoImpl() {
        super(Client.class);
    }

    @Override
    public List<Client> getAllClientByName(String personName) {
        return null;
    }

    public Client getClientById(Long id) {
        return null;
    }

}
