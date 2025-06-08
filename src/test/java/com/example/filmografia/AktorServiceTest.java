package com.example.filmografia;

import com.example.filmografia.repository.AktorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class AktorServiceTest {

    @Autowired
    private AktorService aktorService;

    @Autowired
    private AktorRepository aktorRepository;

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
