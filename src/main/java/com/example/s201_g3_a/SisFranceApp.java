package com.example.s201_g3_a;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Cette classe est la classe principale de l'application SisFranceApp.
 * Elle hérite de la classe Application de JavaFX et initialise l'interface utilisateur.
 */
public class SisFranceApp extends Application {


    /**
     * Méthode start() de JavaFX qui est appelée au démarrage de l'application.
     * Elle charge le fichier FXML, crée la scène et affiche la fenêtre principale de l'application.
     *
     * @param stage La fenêtre principale de l'application.
     * @throws IOException En cas d'erreur lors du chargement du fichier FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("javafx.platform", "desktop");
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SisFrance-view.fxml"));

        Parent root = fxmlLoader.load();



        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("SisFranceApp");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Méthode principale qui lance l'application JavaFX.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans cet exemple).
     */
    public static void main(String[] args) {
        launch();
    }
}