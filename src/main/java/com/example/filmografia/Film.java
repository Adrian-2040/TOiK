package com.example.filmografia;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "filmy")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tytul;

    @Column(name = "rok_produkcji")
    private int rokProdukcji;

    @Column(nullable = false)
    private String rezyser;

    private String gatunek;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "aktorzy_filmy",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "aktor_id"))
    private List<Aktor> aktorzy = new ArrayList<>();

    // Konstruktory
    public Film() {}

    public Film(String tytul, int rokProdukcji, String rezyser, String gatunek) {
        this.tytul = tytul;
        this.rokProdukcji = rokProdukcji;
        this.rezyser = rezyser;
        this.gatunek = gatunek;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public int getRokProdukcji() {
        return rokProdukcji;
    }

    public void setRokProdukcji(int rokProdukcji) {
        this.rokProdukcji = rokProdukcji;
    }

    public String getRezyser() {
        return rezyser;
    }

    public void setRezyser(String rezyser) {
        this.rezyser = rezyser;
    }

    public String getGatunek() {
        return gatunek;
    }

    public void setGatunek(String gatunek) {
        this.gatunek = gatunek;
    }

    public List<Aktor> getAktorzy() {
        return aktorzy;
    }

    public void setAktorzy(List<Aktor> aktorzy) {
        this.aktorzy = aktorzy;
    }

    // Metody pomocnicze
    public void dodajAktora(Aktor aktor) {
        if (!aktorzy.contains(aktor)) {
            aktorzy.add(aktor);
            aktor.getFilmy().add(this);
        }
    }

    public void usunAktora(Aktor aktor) {
        if (aktorzy.contains(aktor)) {
            aktorzy.remove(aktor);
            aktor.getFilmy().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", tytul='" + tytul + '\'' +
                ", rokProdukcji=" + rokProdukcji +
                ", rezyser='" + rezyser + '\'' +
                ", gatunek='" + gatunek + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return id != null && id.equals(film.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
