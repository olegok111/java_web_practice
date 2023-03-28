package msu.cmc.jaweb.dao;

import msu.cmc.jaweb.models.Film;

import java.util.List;

public interface FilmDao extends CommonDao<Film, Long> {

    List<Film> getAllFilmByTitle(String filmTitle);
    List<Film> getAllFilmByYear(Long from, Long to);
    List<Film> getAllFilmByGenre(String genre);
    List<Film> getAllFilmByCompany(String companyName);
    List<Film> getAllFilmByDirector(String director);
    List<Film> getAllFilmByRentPrice(Long from, Long to);
    List<Film> getAllFilmByPurchasePrice(Long from, Long to);
    Film getFilmById(Long id);
    // TODO: replace this method with generic getById method

}
