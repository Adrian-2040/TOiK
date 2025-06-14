package com.example.filmografia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.filmografia.Aktor;

import java.util.Optional;

/**
 * Repozytorium JPA do operacji na encji {@link Aktor}.
 * Zapewnia podstawowe operacje CRUD oraz dodatkowe metody związane z aktorami.
 */
public interface AktorRepository extends JpaRepository<Aktor, Long> {

    /**
     * Znajduje aktora po jego identyfikatorze wraz z załadowanymi filmami, w których wystąpił.
     * Metoda używa zapytania JPQL z LEFT JOIN FETCH, aby pobrać powiązane dane w jednym zapytaniu,
     * unikając problemu N+1.
     *
     * @param id identyfikator aktora
     * @return obiekt {@link Optional} zawierający aktora z załadowanymi filmami, jeśli został znaleziony
     */
    @Query("SELECT a FROM Aktor a LEFT JOIN FETCH a.filmy WHERE a.id = :id")
    Optional<Aktor> findByIdWithFilmy(@Param("id") Long id);
}
