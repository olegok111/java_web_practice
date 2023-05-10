package msu.cmc.jaweb.controllers;

import msu.cmc.jaweb.dao.ClientDao;
import msu.cmc.jaweb.dao.FilmDao;
import msu.cmc.jaweb.dao.RentalDao;
import msu.cmc.jaweb.dao.impl.ClientDaoImpl;
import msu.cmc.jaweb.dao.impl.FilmDaoImpl;
import msu.cmc.jaweb.dao.impl.RentalDaoImpl;
import msu.cmc.jaweb.models.Client;
import msu.cmc.jaweb.models.Film;
import msu.cmc.jaweb.models.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static msu.cmc.jaweb.models.Rental.RentalMethod.*;

@Controller
public class RentalController {
    
    @Autowired
    private final RentalDao rentalDao = new RentalDaoImpl();
    @Autowired
    private final ClientDao clientDao = new ClientDaoImpl();
    @Autowired
    private final FilmDao filmDao = new FilmDaoImpl();

    @GetMapping("/rentalAdd")
    public String rentalAddPage(@RequestParam(name = "clientId") Long clientId, Model model) {
        Client client = clientDao.getById(clientId);
        List<Film> films = (List<Film>) filmDao.getAll();

        if (client == null) {
            model.addAttribute("error_msg", "В базе нет криента с ID " + clientId);
            return "error";
        }

        if (films.isEmpty()) {
            model.addAttribute("error_msg", "В базе нет ни одного фильма.");
            return "error";
        }

        model.addAttribute("client", client);
        model.addAttribute("films", films);
        model.addAttribute("RENT", RENT);
        model.addAttribute("PURCHASE", PURCHASE);
        return "rentalAdd";
    }
    
    @GetMapping("/rentals")
    public String rentalsPage(Model model) {
        List<Rental> rentals = (List<Rental>) rentalDao.getAll();
        model.addAttribute("rentals", rentals);
        model.addAttribute("RENT", RENT);
        model.addAttribute("PURCHASE", PURCHASE);
        return "rentals";
    }
}
