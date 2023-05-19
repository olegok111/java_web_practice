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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static msu.cmc.jaweb.models.Rental.RentalMethod.*;

@Controller
public class RentalController {
    
    @Autowired
    private final RentalDao rentalDao = new RentalDaoImpl();
    @Autowired
    private final ClientDao clientDao = new ClientDaoImpl();
    @Autowired
    private final FilmDao filmDao = new FilmDaoImpl();

    @GetMapping("/rentals")
    public String rentalsPage(Model model) {
        List<Rental> rentals = (List<Rental>) rentalDao.getAll();
        model.addAttribute("rentals", rentals);
        model.addAttribute("RENT", RENT);
        model.addAttribute("PURCHASE", PURCHASE);
        model.addAttribute("search", false);
        return "rentals";
    }

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
    
    @GetMapping("/rentalSearch")
    public String rentalSearch(@RequestParam(name = "from", required = false) String fromTimeStr,
                               @RequestParam(name = "to", required = false) String toTimeStr,
                               Model model) {
        Timestamp fromTime, toTime;

        if (fromTimeStr == null || fromTimeStr.isBlank()) {
            fromTime = null;
        } else {
            LocalDateTime fromTimeLdt = LocalDateTime.parse(fromTimeStr);
            fromTime = Timestamp.valueOf(fromTimeLdt);
        }

        if (toTimeStr == null || toTimeStr.isBlank()) {
            toTime = null;
        } else {
            LocalDateTime toTimeLdt = LocalDateTime.parse(toTimeStr);
            toTime = Timestamp.valueOf(toTimeLdt);
        }

        List<Rental> res = rentalDao.getAllRentalByPeriod(fromTime, toTime);

        if (res == null) {
            res = new ArrayList<>();
        }

        model.addAttribute("rentals", res);
        model.addAttribute("search", true);
        return "rentals";
    }
    
    @PostMapping("/rentalSave")
    public String rentalSave(@RequestParam(name = "clientId") Long clientId,
                             @RequestParam(name = "filmId") Long filmId,
                             @RequestParam(name = "rentalMethod") Rental.RentalMethod rentalMethod,
                             @RequestParam(name = "startTime") String startTimeStr) {
        Client client = clientDao.getById(clientId);
        Film film = filmDao.getById(filmId);
        LocalDateTime startTimeLdt = LocalDateTime.parse(startTimeStr);
        Timestamp startTime = Timestamp.valueOf(startTimeLdt);
        Timestamp endTime;
        Long price;

        if (rentalMethod == RENT) {
            // every rent lasts a day (or 86_400_000 millis)
            endTime = new Timestamp(startTime.getTime() + TimeUnit.DAYS.toMillis(1));
            price = film.getRent_price();
        } else {
            endTime = null;
            price = film.getPurchase_price();
        }

        Rental rental = new Rental(null, film, client, rentalMethod, startTime, endTime, price);
        rentalDao.save(rental);
        return "redirect:/rentals";
    }

    @PostMapping("/rentalDelete")
    public String rentalDelete(@RequestParam(name = "rentalId") Long rentalId) {
        rentalDao.deleteById(rentalId);
        return "redirect:/rentals";
    }
}
