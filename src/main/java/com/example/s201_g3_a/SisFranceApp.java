package com.example.s201_g3_a;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SisFranceApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("javafx.platform", "desktop");
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SisFrance-view.fxml"));
        Parent root = fxmlLoader.load();

        SisFranceView view = fxmlLoader.getController();
        SisFranceViewModel viewModel = new SisFranceViewModel(new SisFranceModel());
        view.setViewModel(viewModel);


        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("SisFranceApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}