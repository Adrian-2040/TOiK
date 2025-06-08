package com.example.filmografia;

import com.example.filmografia.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public Film dodajFilm(Film film) {
        return filmRepository.save(film);
    }

    public Film aktualizujFilm(Film film) {
        return filmRepository.save(film);
    }

    public void usunFilm(Long id) {
        filmRepository.deleteById(id);
    }

    public Film znajdzFilmPoId(Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    public List<Film> znajdzWszystkieFilmy() {
        return filmRepository.findAll();
    }
}
