package com.example.filmografia;

import com.example.filmografia.repository.AktorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy integracyjne dla aplikacji Filmografia.
 * <p>
 * Testują podstawowe operacje CRUD na encji {@link Aktor} za pomocą serwisu {@link AktorService}.
 * <p>
 * Używają wbudowanej bazy H2 w pamięci oraz automatycznego tworzenia i usuwania schematu bazy danych.
 */
@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class FilmografiaApplicationTests {

    @Autowired
    private AktorService aktorService;

    @Autowired
    private AktorRepository aktorRepository;

    /**
     * Testuje dodanie nowego aktora do bazy danych.
     * Sprawdza, czy zwrócony obiekt ma poprawnie ustawione pola i nie jest nullem.
     */
    @Test
    public void testDodajAktora() {
        // Given
        Aktor aktor = new Aktor();
        aktor.setImie("Jan");
        aktor.setNazwisko("Kowalski");
        aktor.setRokDebiutu(2000);

        // When
        Aktor zapisanyAktor = aktorService.dodajAktora(aktor);

        // Then
        assertNotNull(zapisanyAktor);
        assertNotNull(zapisanyAktor.getId());
        assertEquals("Jan", zapisanyAktor.getImie());
        assertEquals("Kowalski", zapisanyAktor.getNazwisko());
        assertEquals(2000, zapisanyAktor.getRokDebiutu());
    }

    /**
     * Testuje wyszukiwanie aktora po identyfikatorze.
     * Sprawdza, czy zwrócony aktor jest niepusty i ma oczekiwane dane.
     */
    @Test
    public void testZnajdzAktoraPoId() {
        // Given
        Aktor aktor = new Aktor();
        aktor.setImie("Anna");
        aktor.setNazwisko("Nowak");
        aktor.setRokDebiutu(1995);
        Aktor zapisanyAktor = aktorService.dodajAktora(aktor);

        // When
        Aktor znalezionyAktor = aktorService.znajdzAktoraPoId(zapisanyAktor.getId());

        // Then
        assertNotNull(znalezionyAktor);
        assertEquals("Anna", znalezionyAktor.getImie());
        assertEquals("Nowak", znalezionyAktor.getNazwisko());
    }

    /**
     * Testuje usunięcie aktora z bazy danych.
     * Sprawdza, czy po usunięciu aktora nie można go już znaleźć.
     */
    @Test
    public void testUsunAktora() {
        // Given
        Aktor aktor = new Aktor();
        aktor.setImie("Piotr");
        aktor.setNazwisko("Testowy");
        aktor.setRokDebiutu(1990);
        Aktor zapisanyAktor = aktorService.dodajAktora(aktor);

        // When
        aktorService.usunAktora(zapisanyAktor.getId());

        // Then
        Aktor usuniety = aktorService.znajdzAktoraPoId(zapisanyAktor.getId());
        assertNull(usuniety);
    }

    /**
     * Testuje pobieranie listy wszystkich aktorów.
     * Sprawdza, czy po dodaniu nowych aktorów ich liczba na liście zwiększa się odpowiednio.
     */
    @Test
    public void testZnajdzWszystkichAktorow() {
        // Given
        long poczatkowaLiczba = aktorService.znajdzWszystkichAktorow().size();

        Aktor aktor1 = new Aktor("Adam", "Pierwszy", 1980);
        Aktor aktor2 = new Aktor("Beata", "Druga", 1985);

        aktorService.dodajAktora(aktor1);
        aktorService.dodajAktora(aktor2);

        // When
        var wszyscyAktorzy = aktorService.znajdzWszystkichAktorow();

        // Then
        assertEquals(poczatkowaLiczba + 2, wszyscyAktorzy.size());
    }

}
