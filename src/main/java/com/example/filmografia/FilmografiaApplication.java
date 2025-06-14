package com.example.filmografia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Główna klasa aplikacji Filmografia, łącząca Spring Boot i JavaFX.
 * <p>
 * Aplikacja uruchamia kontekst Springa i ładuje interfejs graficzny JavaFX
 * z wykorzystaniem FXML. Dzięki integracji z Spring Bootem kontrolery JavaFX
 * mogą korzystać z komponentów i serwisów Springa.
 */
@SpringBootApplication
public class FilmografiaApplication extends Application {

    private ConfigurableApplicationContext springContext;

    /**
     * Główna metoda uruchamiająca aplikację JavaFX.
     *
     * @param args argumenty uruchomieniowe
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Inicjalizuje kontekst Spring Boot przy starcie aplikacji JavaFX.
     *
     * @throws Exception w przypadku błędu inicjalizacji
     */
    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(FilmografiaApplication.class);
    }

    /**
     * Tworzy główne okno aplikacji i ładuje layout z pliku FXML.
     * Umożliwia automatyczne wstrzykiwanie kontrolerów przy pomocy Springa.
     *
     * @param primaryStage główna scena aplikacji JavaFX
     * @throws Exception w przypadku błędu ładowania FXML
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Layout.fxml"));
        loader.setControllerFactory(springContext::getBean);  // Pozwala Springowi utworzyć kontroler

        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("System Zarządzania Filmografią Aktorów");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Zamyka kontekst Springa przy wyjściu z aplikacji.
     *
     * @throws Exception w przypadku błędu zamykania
     */
    @Override
    public void stop() throws Exception {
        if (springContext != null) {
            springContext.close();
        }
    }
}

