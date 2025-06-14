package com.example.filmografia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.filmografia.Film;

/**
 * Repozytorium JPA dla encji {@link Film}.
 * Udostępnia podstawowe operacje CRUD (Create, Read, Update, Delete) na obiektach filmu.
 */
public interface FilmRepository extends JpaRepository<Film, Long> {
}
