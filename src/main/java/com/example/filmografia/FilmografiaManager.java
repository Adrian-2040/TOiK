package com.example.filmografia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serwis zarządzający relacjami między aktorami a filmami.
 * <p>
 * Umożliwia przypisywanie i usuwanie aktorów z filmów oraz dba o spójność relacji
 * dwukierunkowej między encjami {@link Aktor} i {@link Film}.
 */
@Service
public class FilmografiaManager {

    @Autowired
    private AktorService aktorService;

    @Autowired
    private FilmService filmService;

    /**
     * Przypisuje aktora do filmu.
     * <p>
     * Metoda dodaje aktora do listy aktorów w filmie oraz film do listy filmów aktora,
     * a następnie aktualizuje obie encje w bazie danych.
     *
     * @param aktorId identyfikator aktora
     * @param filmId  identyfikator filmu
     */
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

    /**
     * Usuwa przypisanie aktora z filmu.
     * <p>
     * Metoda usuwa aktora z listy aktorów filmu oraz film z listy filmów aktora,
     * a następnie aktualizuje obie encje w bazie danych.
     *
     * @param aktorId identyfikator aktora
     * @param filmId  identyfikator filmu
     */
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

