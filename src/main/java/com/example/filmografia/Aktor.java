package com.example.filmografia;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje aktora w systemie filmografii.
 * Encja odwzorowująca tabelę {@code aktorzy} w bazie danych.
 */
@Entity
@Table(name = "aktorzy")
public class Aktor {

    /**
     * Unikalny identyfikator aktora (generowany automatycznie).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Imię aktora (pole wymagane).
     */
    @Column(nullable = false)
    private String imie;

    /**
     * Nazwisko aktora (pole wymagane).
     */
    @Column(nullable = false)
    private String nazwisko;

    /**
     * Rok debiutu aktora.
     */
    @Column(name = "rok_debiutu")
    private int rokDebiutu;

    /**
     * Lista filmów, w których aktor wystąpił.
     * Relacja wiele-do-wielu z encją {@link Film}.
     */
    @ManyToMany(mappedBy = "aktorzy", fetch = FetchType.LAZY)
    private List<Film> filmy = new ArrayList<>();

    /**
     * Konstruktor domyślny wymagany przez JPA.
     */
    public Aktor() {}

    /**
     * Konstruktor tworzący aktora z podanymi danymi.
     *
     * @param imie       imię aktora
     * @param nazwisko   nazwisko aktora
     * @param rokDebiutu rok debiutu aktora
     */
    public Aktor(String imie, String nazwisko, int rokDebiutu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.rokDebiutu = rokDebiutu;
    }

    // Gettery i settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public int getRokDebiutu() {
        return rokDebiutu;
    }

    public void setRokDebiutu(int rokDebiutu) {
        this.rokDebiutu = rokDebiutu;
    }

    public List<Film> getFilmy() {
        return filmy;
    }

    public void setFilmy(List<Film> filmy) {
        this.filmy = filmy;
    }

    /**
     * Zwraca pełne imię i nazwisko aktora w postaci jednego ciągu tekstowego.
     *
     * @return imię i nazwisko oddzielone spacją
     */
    public String getPelneImie() {
        return imie + " " + nazwisko;
    }

    @Override
    public String toString() {
        return "Aktor{" +
                "id=" + id +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", rokDebiutu=" + rokDebiutu +
                '}';
    }

    /**
     * Porównuje obiekty na podstawie identyfikatora.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aktor)) return false;
        Aktor aktor = (Aktor) o;
        return id != null && id.equals(aktor.id);
    }

    /**
     * Zwraca hash code klasy (zgodny z equals).
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
