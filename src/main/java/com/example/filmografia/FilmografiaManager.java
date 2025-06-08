package com.example.filmografia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmografiaManager {

    @Autowired
    private AktorService aktorService;

    @Autowired
    private FilmService filmService;

    // Metody do zarządzania relacjami aktor-film
    public void przypiszAktoraDoFilmu(Long aktorId, Long filmId) {
        Aktor aktor = aktorService.znajdzAktoraPoId(aktorId);
        Film film = filmService.znajdzFilmPoId(filmId);

        if (aktor != null && film != null) {
            film.getAktorzy().add(aktor);
            aktor.getFilmy().add(film);
            filmService.aktualizujFilm(film);
            aktorService.aktualizujAktora(aktor);
        }
    }

    public void usunAktoraZFilmu(Long aktorId, Long filmId) {
        Aktor aktor = aktorService.znajdzAktoraPoId(aktorId);
        Film film = filmService.znajdzFilmPoId(filmId);

        if (aktor != null && film != null) {
            film.getAktorzy().remove(aktor);
            aktor.getFilmy().remove(film);
            filmService.aktualizujFilm(film);
            aktorService.aktualizujAktora(aktor);
        }
    }
}
