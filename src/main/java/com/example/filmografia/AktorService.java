package com.example.filmografia;

import com.example.filmografia.repository.AktorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serwis odpowiedzialny za logikę biznesową związaną z obsługą aktorów.
 * Zapewnia metody do tworzenia, modyfikowania, usuwania oraz wyszukiwania aktorów.
 */
@Service
public class AktorService {

    @Autowired
    private AktorRepository aktorRepository;

    /**
     * Zapisuje nowego aktora w bazie danych.
     *
     * @param aktor obiekt {@link Aktor} do zapisania
     * @return zapisany obiekt aktora
     */
    public Aktor dodajAktora(Aktor aktor) {
        return aktorRepository.save(aktor);
    }

    /**
     * Aktualizuje dane istniejącego aktora.
     *
     * @param aktor obiekt {@link Aktor} z uaktualnionymi danymi
     * @return zaktualizowany obiekt aktora
     */
    public Aktor aktualizujAktora(Aktor aktor) {
        return aktorRepository.save(aktor);
    }

    /**
     * Usuwa aktora z bazy danych na podstawie ID.
     * Operacja obejmuje odłączenie aktora od wszystkich powiązanych filmów.
     * Operacja odbywa się w transakcji.
     *
     * @param id identyfikator aktora do usunięcia
     */
    @Transactional
    public void usunAktora(Long id) {
        // Używamy zapytania, które ładuje aktora wraz z powiązanymi filmami
        Aktor aktor = aktorRepository.findByIdWithFilmy(id).orElse(null);
        if (aktor != null) {
            // Usuwamy powiązania aktora z filmami
            aktor.getFilmy().forEach(film -> {
                film.getAktorzy().remove(aktor);
            });

            // Czyścimy listę filmów u aktora
            aktor.getFilmy().clear();

            // Usuwamy samego aktora
            aktorRepository.deleteById(id);
        }
    }

    /**
     * Wyszukuje aktora po jego identyfikatorze.
     *
     * @param id identyfikator aktora
     * @return obiekt {@link Aktor} lub {@code null} jeśli nie znaleziono
     */
    public Aktor znajdzAktoraPoId(Long id) {
        return aktorRepository.findById(id).orElse(null);
    }

    /**
     * Zwraca listę wszystkich aktorów zapisanych w bazie danych.
     *
     * @return lista obiektów {@link Aktor}
     */
    public List<Aktor> znajdzWszystkichAktorow() {
        return aktorRepository.findAll();
    }
}

