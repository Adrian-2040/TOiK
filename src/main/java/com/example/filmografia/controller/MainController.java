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

@Component
public class MainController implements Initializable {

    @Autowired
    private AktorService aktorService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmografiaManager filmografiaManager;

    // Komponenty dla aktorów
    @FXML private TableView<Aktor> tabelaAktorow;
    @FXML private TableColumn<Aktor, String> kolumnaImie;
    @FXML private TableColumn<Aktor, String> kolumnaNazwisko;
    @FXML private TableColumn<Aktor, Integer> kolumnaRokDebiutu;

    @FXML private TextField poleImie;
    @FXML private TextField poleNazwisko;
    @FXML private TextField poleRokDebiutu;

    // Komponenty dla filmów
    @FXML private TableView<Film> tabelaFilmow;
    @FXML private TableColumn<Film, String> kolumnaTytul;
    @FXML private TableColumn<Film, Integer> kolumnaRokProdukcji;
    @FXML private TableColumn<Film, String> kolumnaRezyser;
    @FXML private TableColumn<Film, String> kolumnaGatunek;

    @FXML private TextField poleTytul;
    @FXML private TextField poleRokProdukcji;
    @FXML private TextField poleRezyser;
    @FXML private TextField poleGatunek;

    // Komponenty dla wyszukiwania
    @FXML private TextField poleWyszukiwania;
    @FXML private ComboBox<String> comboKryterium;

    private ObservableList<Aktor> listaAktorow = FXCollections.observableArrayList();
    private ObservableList<Film> listaFilmow = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Konfiguracja tabel
        kolumnaImie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        kolumnaRokDebiutu.setCellValueFactory(new PropertyValueFactory<>("rokDebiutu"));

        kolumnaTytul.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        kolumnaRokProdukcji.setCellValueFactory(new PropertyValueFactory<>("rokProdukcji"));
        kolumnaRezyser.setCellValueFactory(new PropertyValueFactory<>("rezyser"));
        kolumnaGatunek.setCellValueFactory(new PropertyValueFactory<>("gatunek"));

        tabelaAktorow.setItems(listaAktorow);
        tabelaFilmow.setItems(listaFilmow);

        // Konfiguracja ComboBox
        comboKryterium.setItems(FXCollections.observableArrayList(
                "Imię", "Nazwisko", "Rok debiutu", "Tytuł filmu", "Reżyser", "Gatunek"
        ));
        comboKryterium.getSelectionModel().selectFirst();

        // Ładowanie danych
        odswiezDane();
    }

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

    @FXML
    private void usunAktora() {
        Aktor wybranyAktor = tabelaAktorow.getSelectionModel().getSelectedItem();
        if (wybranyAktor != null) {
            aktorService.usunAktora(wybranyAktor.getId());
            odswiezDane();
            pokazInfo("Sukces", "Aktor został usunięty!");
        } else {
            pokazAlert("Błąd", "Wybierz aktora do usunięcia!");
        }
    }

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

    @FXML
    private void wyszukaj() {
        String kryterium = poleWyszukiwania.getText().trim();
        if (kryterium.isEmpty()) {
            odswiezDane();
            return;
        }

        // Implementacja wyszukiwania - uproszczona wersja
        listaAktorow.clear();
        listaFilmow.clear();

        // Wyszukiwanie w aktorach
        aktorService.znajdzWszystkichAktorow().stream()
                .filter(aktor -> aktor.getImie().toLowerCase().contains(kryterium.toLowerCase()) ||
                        aktor.getNazwisko().toLowerCase().contains(kryterium.toLowerCase()))
                .forEach(listaAktorow::add);

        // Wyszukiwanie w filmach
        filmService.znajdzWszystkieFilmy().stream()
                .filter(film -> film.getTytul().toLowerCase().contains(kryterium.toLowerCase()) ||
                        film.getRezyser().toLowerCase().contains(kryterium.toLowerCase()) ||
                        film.getGatunek().toLowerCase().contains(kryterium.toLowerCase()))
                .forEach(listaFilmow::add);
    }

    @FXML
    private void odswiezDane() {
        listaAktorow.clear();
        listaFilmow.clear();

        listaAktorow.addAll(aktorService.znajdzWszystkichAktorow());
        listaFilmow.addAll(filmService.znajdzWszystkieFilmy());
    }

    private void wyczyscPolaAktora() {
        poleImie.clear();
        poleNazwisko.clear();
        poleRokDebiutu.clear();
    }

    private void wyczyscPolaFilmu() {
        poleTytul.clear();
        poleRokProdukcji.clear();
        poleRezyser.clear();
        poleGatunek.clear();
    }

    private void pokazAlert(String tytul, String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(tytul);
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }

    private void pokazInfo(String tytul, String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tytul);
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }
}
