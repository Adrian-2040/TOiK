package com.example.filmografia.controller;

import com.example.filmografia.Aktor;
import com.example.filmografia.Film;
import com.example.filmografia.AktorService;
import com.example.filmografia.FilmService;
import com.example.filmografia.FilmografiaManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler głównego widoku aplikacji Filmografia.
 * Obsługuje operacje związane z dodawaniem, usuwaniem, wyszukiwaniem oraz wyświetlaniem aktorów i filmów.
 * Współpracuje z serwisami: {@link AktorService}, {@link FilmService}, {@link FilmografiaManager}.
 * Używa komponentów JavaFX oraz Spring Framework (komponent @Component).
 */
@Component
public class MainController implements Initializable {

    @Autowired
    private AktorService aktorService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmografiaManager filmografiaManager;

    // --- Komponenty widoku dla aktorów ---

    /** Tabela wyświetlająca aktorów */
    @FXML private TableView<Aktor> tabelaAktorow;
    @FXML private TableColumn<Aktor, String> kolumnaImie;
    @FXML private TableColumn<Aktor, String> kolumnaNazwisko;
    @FXML private TableColumn<Aktor, Integer> kolumnaRokDebiutu;

    @FXML private TextField poleImie;
    @FXML private TextField poleNazwisko;
    @FXML private TextField poleRokDebiutu;

    // --- Komponenty widoku dla filmów ---

    /** Tabela wyświetlająca filmy */
    @FXML private TableView<Film> tabelaFilmow;
    @FXML private TableColumn<Film, String> kolumnaTytul;
    @FXML private TableColumn<Film, Integer> kolumnaRokProdukcji;
    @FXML private TableColumn<Film, String> kolumnaRezyser;
    @FXML private TableColumn<Film, String> kolumnaGatunek;

    @FXML private TextField poleTytul;
    @FXML private TextField poleRokProdukcji;
    @FXML private TextField poleRezyser;
    @FXML private TextField poleGatunek;

    // --- Komponenty wyszukiwania ---

    @FXML private TextField poleWyszukiwania;
    @FXML private ComboBox<String> comboKryterium;

    private ObservableList<Aktor> listaAktorow = FXCollections.observableArrayList();
    private ObservableList<Film> listaFilmow = FXCollections.observableArrayList();

    /**
     * Inicjalizuje komponenty interfejsu użytkownika po załadowaniu FXML.
     * Konfiguruje tabele i komponenty wyszukiwania, ładuje dane z bazy.
     *
     * @param url            nieużywane
     * @param resourceBundle nieużywane
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kolumnaImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        kolumnaRokDebiutu.setCellValueFactory(new PropertyValueFactory<>("rokDebiutu"));

        kolumnaTytul.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        kolumnaRokProdukcji.setCellValueFactory(new PropertyValueFactory<>("rokProdukcji"));
        kolumnaRezyser.setCellValueFactory(new PropertyValueFactory<>("rezyser"));
        kolumnaGatunek.setCellValueFactory(new PropertyValueFactory<>("gatunek"));

        tabelaAktorow.setItems(listaAktorow);
        tabelaFilmow.setItems(listaFilmow);

        comboKryterium.setItems(FXCollections.observableArrayList(
                "Imię", "Nazwisko", "Rok debiutu", "Tytuł filmu", "Reżyser", "Gatunek"
        ));
        comboKryterium.getSelectionModel().selectFirst();

        odswiezDane();
    }

    /**
     * Dodaje nowego aktora do bazy danych na podstawie danych z formularza.
     * Obsługuje walidację danych i wyjątki.
     */
    @FXML
    private void dodajAktora() {
        try {
            String imie = poleImie.getText().trim();
            String nazwisko = poleNazwisko.getText().trim();
            int rokDebiutu = Integer.parseInt(poleRokDebiutu.getText().trim());

            if (imie.isEmpty() || nazwisko.isEmpty()) {
                pokazAlert("Błąd", "Imię i nazwisko nie mogą być puste!");
                return;
            }

            Aktor nowyAktor = new Aktor();
            nowyAktor.setImie(imie);
            nowyAktor.setNazwisko(nazwisko);
            nowyAktor.setRokDebiutu(rokDebiutu);

            aktorService.dodajAktora(nowyAktor);
            wyczyscPolaAktora();
            odswiezDane();

            pokazInfo("Sukces", "Aktor został dodany pomyślnie!");

        } catch (NumberFormatException e) {
            pokazAlert("Błąd", "Rok debiutu musi być liczbą!");
        } catch (Exception e) {
            pokazAlert("Błąd", "Wystąpił błąd podczas dodawania aktora: " + e.getMessage());
        }
    }

    /**
     * Dodaje nowy film do bazy danych na podstawie danych z formularza.
     * Obsługuje walidację danych i wyjątki.
     */
    @FXML
    private void dodajFilm() {
        try {
            String tytul = poleTytul.getText().trim();
            String rezyser = poleRezyser.getText().trim();
            String gatunek = poleGatunek.getText().trim();
            int rokProdukcji = Integer.parseInt(poleRokProdukcji.getText().trim());

            if (tytul.isEmpty() || rezyser.isEmpty()) {
                pokazAlert("Błąd", "Tytuł i reżyser nie mogą być puste!");
                return;
            }

            Film nowyFilm = new Film();
            nowyFilm.setTytul(tytul);
            nowyFilm.setRezyser(rezyser);
            nowyFilm.setGatunek(gatunek);
            nowyFilm.setRokProdukcji(rokProdukcji);

            filmService.dodajFilm(nowyFilm);
            wyczyscPolaFilmu();
            odswiezDane();

            pokazInfo("Sukces", "Film został dodany pomyślnie!");

        } catch (NumberFormatException e) {
            pokazAlert("Błąd", "Rok produkcji musi być liczbą!");
        } catch (Exception e) {
            pokazAlert("Błąd", "Wystąpił błąd podczas dodawania filmu: " + e.getMessage());
        }
    }

    /**
     * Usuwa zaznaczonego aktora z bazy danych.
     * Wyświetla komunikat błędu, jeśli nic nie jest zaznaczone.
     */
    @FXML
    private void usunAktora() {
        Aktor wybranyAktor = tabelaAktorow.getSelectionModel().getSelectedItem();
        if (wybranyAktor != null) {
            try {
                aktorService.usunAktora(wybranyAktor.getId());
                odswiezDane();
                pokazInfo("Sukces", "Aktor został usunięty!");
            } catch (Exception e) {
                pokazAlert("Błąd", "Wystąpił błąd podczas usuwania aktora: " + e.getMessage());
            }
        } else {
            pokazAlert("Błąd", "Wybierz aktora do usunięcia!");
        }
    }

    /**
     * Usuwa zaznaczony film z bazy danych.
     * Wyświetla komunikat błędu, jeśli nic nie jest zaznaczone.
     */
    @FXML
    private void usunFilm() {
        Film wybranyFilm = tabelaFilmow.getSelectionModel().getSelectedItem();
        if (wybranyFilm != null) {
            filmService.usunFilm(wybranyFilm.getId());
            odswiezDane();
            pokazInfo("Sukces", "Film został usunięty!");
        } else {
            pokazAlert("Błąd", "Wybierz film do usunięcia!");
        }
    }

    /**
     * Wyszukuje aktorów lub filmy na podstawie wybranego kryterium i wartości w polu wyszukiwania.
     * Obsługuje różne tryby wyszukiwania i walidację danych wejściowych.
     */
    @FXML
    private void wyszukaj() {
        String kryterium = poleWyszukiwania.getText().trim();
        String wybraneKryterium = comboKryterium.getSelectionModel().getSelectedItem();

        if (kryterium.isEmpty()) {
            odswiezDane();
            return;
        }

        listaAktorow.clear();
        listaFilmow.clear();

        try {
            switch (wybraneKryterium) {
                case "Imię":
                    aktorService.znajdzWszystkichAktorow().stream()
                            .filter(aktor -> aktor.getImie().toLowerCase().contains(kryterium.toLowerCase()))
                            .forEach(listaAktorow::add);
                    break;

                case "Nazwisko":
                    aktorService.znajdzWszystkichAktorow().stream()
                            .filter(aktor -> aktor.getNazwisko().toLowerCase().contains(kryterium.toLowerCase()))
                            .forEach(listaAktorow::add);
                    break;

                case "Rok debiutu":
                    try {
                        int rokDebiutu = Integer.parseInt(kryterium);
                        aktorService.znajdzWszystkichAktorow().stream()
                                .filter(aktor -> aktor.getRokDebiutu() == rokDebiutu)
                                .forEach(listaAktorow::add);
                    } catch (NumberFormatException e) {
                        pokazAlert("Błąd", "Rok debiutu musi być liczbą!");
                        return;
                    }
                    break;

                case "Tytuł filmu":
                    filmService.znajdzWszystkieFilmy().stream()
                            .filter(film -> film.getTytul().toLowerCase().contains(kryterium.toLowerCase()))
                            .forEach(listaFilmow::add);
                    break;

                case "Reżyser":
                    filmService.znajdzWszystkieFilmy().stream()
                            .filter(film -> film.getRezyser().toLowerCase().contains(kryterium.toLowerCase()))
                            .forEach(listaFilmow::add);
                    break;

                case "Gatunek":
                    filmService.znajdzWszystkieFilmy().stream()
                            .filter(film -> film.getGatunek() != null &&
                                    film.getGatunek().toLowerCase().contains(kryterium.toLowerCase()))
                            .forEach(listaFilmow::add);
                    break;

                default:
                    // domyślne wyszukiwanie
                    aktorService.znajdzWszystkichAktorow().stream()
                            .filter(aktor -> aktor.getImie().toLowerCase().contains(kryterium.toLowerCase()) ||
                                    aktor.getNazwisko().toLowerCase().contains(kryterium.toLowerCase()))
                            .forEach(listaAktorow::add);

                    filmService.znajdzWszystkieFilmy().stream()
                            .filter(film -> film.getTytul().toLowerCase().contains(kryterium.toLowerCase()) ||
                                    film.getRezyser().toLowerCase().contains(kryterium.toLowerCase()) ||
                                    (film.getGatunek() != null &&
                                            film.getGatunek().toLowerCase().contains(kryterium.toLowerCase())))
                            .forEach(listaFilmow::add);
                    break;
            }
        } catch (Exception e) {
            pokazAlert("Błąd", "Wystąpił błąd podczas wyszukiwania: " + e.getMessage());
        }
    }

    /**
     * Odświeża listy aktorów i filmów z bazy danych.
     */
    @FXML
    private void odswiezDane() {
        listaAktorow.clear();
        listaFilmow.clear();

        listaAktorow.addAll(aktorService.znajdzWszystkichAktorow());
        listaFilmow.addAll(filmService.znajdzWszystkieFilmy());
    }

    /** Czyści pola formularza aktora. */
    private void wyczyscPolaAktora() {
        poleImie.clear();
        poleNazwisko.clear();
        poleRokDebiutu.clear();
    }

    /** Czyści pola formularza filmu. */
    private void wyczyscPolaFilmu() {
        poleTytul.clear();
        poleRokProdukcji.clear();
        poleRezyser.clear();
        poleGatunek.clear();
    }

    /**
     * Pokazuje alert błędu z podanym tytułem i wiadomością.
     *
     * @param tytul     Tytuł okna alertu.
     * @param wiadomosc Treść wiadomości.
     */
    private void pokazAlert(String tytul, String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(tytul);
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }

    /**
     * Pokazuje alert informacyjny z podanym tytułem i wiadomością.
     *
     * @param tytul     Tytuł okna alertu.
     * @param wiadomosc Treść wiadomości.
     */
    private void pokazInfo(String tytul, String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tytul);
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }
}
