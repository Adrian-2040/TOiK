package com.example.filmografia;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aktorzy")
public class Aktor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imie;

    @Column(nullable = false)
    private String nazwisko;

    @Column(name = "rok_debiutu")
    private int rokDebiutu;

    @ManyToMany(mappedBy = "aktorzy", fetch = FetchType.LAZY)
    private List<Film> filmy = new ArrayList<>();

    // Konstruktory
    public Aktor() {}

    public Aktor(String imie, String nazwisko, int rokDebiutu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.rokDebiutu = rokDebiutu;
    }

    // Getters and setters
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

    // Metody pomocnicze
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aktor)) return false;
        Aktor aktor = (Aktor) o;
        return id != null && id.equals(aktor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
