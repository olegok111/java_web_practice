package msu.cmc.jaweb.controllers;

import msu.cmc.jaweb.dao.impl.ClientDaoImpl;
import msu.cmc.jaweb.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import msu.cmc.jaweb.dao.ClientDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class ClientController {

    @Autowired
    private final ClientDao clientDao = new ClientDaoImpl();

    @GetMapping("/clients")
    public String clientsPage(Model model) {
        List<Client> clients = (List<Client>) clientDao.getAll();
        model.addAttribute("clients", clients);
        model.addAttribute("search", false);
        return "clients";
    }

    @GetMapping("/clientInfo")
    public String clientInfoPage(@RequestParam(name = "clientId") Long clientId, Model model) {
        Client client = clientDao.getById(clientId);

        if (client == null) {
            model.addAttribute("error_msg", "В базе нет криента с ID " + clientId);
            return "error";
        }

        model.addAttribute("clientService", clientDao);
        model.addAttribute("client", client);
        return "clientInfo";
    }

    @GetMapping("/clientEdit")
    public String clientEditPage(@RequestParam(name = "clientId", required = false) Long clientId, Model model) {
        if (clientId == null) {
            model.addAttribute("client", new Client());
            return "clientEdit";
        }

        Client client = clientDao.getById(clientId);

        if (client == null) {
            model.addAttribute("error_msg", "В базе нет клиента с ID " + clientId);
            return "error";
        }

        model.addAttribute("client", client);
        return "clientEdit";
    }

    @GetMapping("/clientSearch")
    public String clientSearch(@RequestParam(name = "fullName", required = false) String fullName,
                               Model model) {
        List<Client> res = clientDao.getAllClientByName(fullName);

        if (res == null) {
            res = new ArrayList<>();
        }

        model.addAttribute("clients", res);
        model.addAttribute("search", true);
        return "clients";
    }

    @PostMapping("/clientSave")
    public String clientSave(@RequestParam(name = "fullName") String fullName,
                             @RequestParam(name = "email") String email,
                             @RequestParam(name = "phone") String phone) {
        Client client = new Client(null, fullName, email, phone);
        clientDao.save(client);
        return String.format("redirect:/clientInfo?clientId=%d", client.getId());
    }

    @PostMapping("/clientDelete")
    public String clientDelete(@RequestParam(name = "clientId") Long clientId) {
        clientDao.deleteById(clientId);
        return "redirect:/clients";
    }
}
