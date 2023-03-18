package msu.cmc.jaweb.dao;

import msu.cmc.jaweb.models.Film;

import java.util.List;

public interface FilmDao extends CommonDao<Film, Long> {

    List<Film> getAllFilmByName(String filmName);
    Film getFilmById(Long id);

}
