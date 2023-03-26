package msu.cmc.jaweb.dao;

import msu.cmc.jaweb.models.Film;
import org.hibernate.Filter;

import java.util.List;

public interface FilmDao extends CommonDao<Film, Long> {

    List<Film> getAllFilmByTitle(String filmTitle);
    List<Film> getAllFilmByFilter(Filter filter);
    Film getFilmById(Long id);

}
