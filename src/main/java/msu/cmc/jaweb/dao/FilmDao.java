package msu.cmc.jaweb.dao;

import msu.cmc.jaweb.models.Film;
import org.postgresql.util.PGmoney;

import java.util.List;

public interface FilmDao extends CommonDao<Film, Long> {

    List<Film> getAllFilmByTitle(String filmTitle);
    List<Film> getAllFilmByYear(Long from, Long to);
    List<Film> getAllFilmByGenre(String genre);
    List<Film> getAllFilmByCompany(String companyName);
    List<Film> getAllFilmByDirector(String director);
    List<Film> getAllFilmByRentPrice(PGmoney from, PGmoney to);
    List<Film> getAllFilmByPurchasePrice(PGmoney from, PGmoney to);
    Film getFilmById(Long id);

}
