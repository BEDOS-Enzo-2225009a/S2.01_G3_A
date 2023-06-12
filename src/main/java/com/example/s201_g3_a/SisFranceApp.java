package com.example.s201_g3_a;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SisFranceApp extends Application {
    public static CustomMapLayer mapLayer;
    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("javafx.platform", "desktop");
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SisFrance-view.fxml"));

        Parent root = fxmlLoader.load();
        SisFranceView view = fxmlLoader.getController();

        // Créez une instance de CustomMapLayer
        mapLayer = new CustomMapLayer();

        // Ajoutez mapLayer à un conteneur parent de la scène principale
        Pane mainPane = new Pane();
        mainPane.getChildren().addAll(root, mapLayer);

        Scene scene = new Scene(mainPane, 1200, 800);
        stage.setTitle("SisFranceApp");
        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args) {
        launch();
    }
}