package com.example.filmografia;

import com.example.filmografia.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serwis odpowiedzialny za operacje CRUD na encji {@link Film}.
 * <p>
 * Umożliwia dodawanie, aktualizowanie, usuwanie oraz pobieranie filmów z bazy danych.
 */
@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    /**
     * Dodaje nowy film do bazy danych.
     *
     * @param film film do dodania
     * @return zapisany film
     */
    public Film dodajFilm(Film film) {
        return filmRepository.save(film);
    }

    /**
     * Aktualizuje istniejący film.
     *
     * @param film film do aktualizacji
     * @return zaktualizowany film
     */
    public Film aktualizujFilm(Film film) {
        return filmRepository.save(film);
    }

    /**
     * Usuwa film z bazy danych na podstawie jego identyfikatora.
     *
     * @param id identyfikator filmu do usunięcia
     */
    public void usunFilm(Long id) {
        filmRepository.deleteById(id);
    }

    /**
     * Znajduje film na podstawie jego identyfikatora.
     *
     * @param id identyfikator filmu
     * @return film, jeśli istnieje; w przeciwnym razie {@code null}
     */
    public Film znajdzFilmPoId(Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    /**
     * Zwraca listę wszystkich filmów zapisanych w bazie danych.
     *
     * @return lista filmów
     */
    public List<Film> znajdzWszystkieFilmy() {
        return filmRepository.findAll();
    }
}


