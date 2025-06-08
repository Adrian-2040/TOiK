package com.example.filmografia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class FilmografiaApplication extends Application {

    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // Uruchomienie JavaFX
        launch(args);
    }

    @Override
    public void init() throws Exception {
        // Uruchomienie Spring Boot context
        springContext = SpringApplication.run(FilmografiaApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Ładowanie głównego okna JavaFX
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        loader.setControllerFactory(springContext::getBean);

        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("System Zarządzania Filmografią Aktorów");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Zamknięcie Spring context
        if (springContext != null) {
            springContext.close();
        }
    }
}
