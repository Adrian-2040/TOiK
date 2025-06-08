package com.example.filmografia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.filmografia.Film;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
