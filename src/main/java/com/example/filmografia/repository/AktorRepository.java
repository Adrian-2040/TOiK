package com.example.filmografia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.filmografia.Aktor;

public interface AktorRepository extends JpaRepository<Aktor, Long> {
}
