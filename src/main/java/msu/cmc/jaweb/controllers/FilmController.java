package msu.cmc.jaweb.controllers;

import msu.cmc.jaweb.dao.impl.FilmDaoImpl;
import msu.cmc.jaweb.models.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import msu.cmc.jaweb.dao.FilmDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FilmController {

    @Autowired
    private final FilmDao filmDao = new FilmDaoImpl();

    @GetMapping("/films")
    public String filmsPage(Model model) {
        return "films";
    }

    @GetMapping("/filmInfo")
    public String filmInfoPage(@RequestParam(name = "filmId") Long filmId, Model model) {
        Film film = filmDao.getById(filmId);

        if (film == null) {
            model.addAttribute("error_msg", "В базе нет фильма с ID " + filmId);
            return "error";
        }

        model.addAttribute("filmService", filmDao);
        model.addAttribute("film", film);
        return "filmInfo";
    }

    @GetMapping("/filmEdit")
    public String filmEditPage(@RequestParam(name = "filmId", required = false) Long filmId, Model model) {
        if (filmId == null) {
            model.addAttribute("film", new Film());
            return "filmEdit";
        }

        Film film = filmDao.getById(filmId);

        if (film == null) {
            model.addAttribute("error_msg", "В базе нет фильма с ID " + filmId);
            return "error";
        }

        model.addAttribute("film", film);
        return "filmEdit";
    }

    @PostMapping("/filmSave")
    public String filmSave(@RequestParam(name = "title") String title,
                           @RequestParam(name = "genre") String genre,
                           @RequestParam(name = "company") String company,
                           @RequestParam(name = "director") String director,
                           @RequestParam(name = "releaseYear") Long releaseYear,
                           @RequestParam(name = "purchasePrice", required = false) Long purchasePrice,
                           @RequestParam(name = "rentPrice", required = false) Long rentPrice) {
        Film film = new Film(null, title, genre, company, director, releaseYear, purchasePrice, rentPrice);
        filmDao.save(film);
        return String.format("redirect:/filmInfo?filmId=%d", film.getId());
    }

    @PostMapping("/filmDelete")
    public String filmDelete(@RequestParam(name = "filmId") Long filmId) {
        filmDao.deleteById(filmId);
        return "redirect:/films";
    }
}