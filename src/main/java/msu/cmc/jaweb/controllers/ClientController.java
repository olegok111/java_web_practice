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

@Controller
public class ClientController {

    @Autowired
    private final ClientDao clientDao = new ClientDaoImpl();

    @GetMapping("/clients")
    public String clientsPage(Model model) {
        return "clients";
    }

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

    @GetMapping("/clientEdit")
    public String clientEditPage(@RequestParam(name = "clientId") Long clientId, Model model) {

        return "clientEdit";
    }

    @PostMapping("/clientSave")
    public String clientSave(@RequestParam(name = "clientId") Long clientId,
                             @RequestParam(name = "fullName") String fullName,
                             @RequestParam(name = "email") String email,
                             @RequestParam(name = "phone") String phone,
                             Model model) {
        Client client = clientDao.getById(clientId);

        if (client == null) {
            client = new Client(clientId, fullName, email, phone);
        } else {
            client.setFull_name(fullName);
            client.setEmail(email);
            client.setPhone(phone);
        }

        model.addAttribute("error_msg", "Данные не сохранены");
        return "error";
    }

    @PostMapping("/clientRemove")
    public String clientRemove(@RequestParam(name = "clientId") Long clientId) {
        clientDao.deleteById(clientId);
        return "redirect:/clients";
    }
}
