package msu.cmc.jaweb.controllers;

import msu.cmc.jaweb.dao.impl.ClientDaoImpl;
import msu.cmc.jaweb.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import msu.cmc.jaweb.dao.ClientDao;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

    @Autowired
    private final ClientDao clientDao = new ClientDaoImpl();

    @GetMapping("/clientInfo")
    public String clientInfoPage(@RequestParam(name = "clientId") Long clientId, Model model) {
        Client client = clientDao.getById(clientId);

        if (client == null) {
            model.addAttribute("error_msg", "В базе нет криента с ID = " + clientId);
            return "error";
        }

        model.addAttribute("clientService", clientDao);
        return "clientInfo";
    }

}
