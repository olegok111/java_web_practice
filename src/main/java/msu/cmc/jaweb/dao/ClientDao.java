package msu.cmc.jaweb.dao;

import msu.cmc.jaweb.models.Client;

import java.util.List;

public interface ClientDao extends CommonDao<Client, Long> {

    List<Client> getAllClientByName(String personName);
    Client getClientById(Long id);
    // TODO: replace this method with generic getById method

}
