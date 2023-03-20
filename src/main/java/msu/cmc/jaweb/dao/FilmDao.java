package msu.cmc.jaweb.dao;

import msu.cmc.jaweb.models.Film;

import java.util.List;

public interface FilmDao extends CommonDao<Film, Long> {

    List<Film> getAllFilmByTitle(String filmTitle);
    Film getFilmById(Long id);

}
