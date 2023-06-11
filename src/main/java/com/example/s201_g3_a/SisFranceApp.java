package com.example.s201_g3_a;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SisFranceApp extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException, InstantiationException, IllegalAccessException {
        System.setProperty("javafx.platform", "desktop");
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
        this.stage = stage;
        this.stage.setTitle("SisFranceApp");
        showScene("SisFrance-view.fxml");


    }

    public static void showScene(String fxmlFileName) throws IOException, InstantiationException, IllegalAccessException {
        FXMLLoader fxmlLoader = new FXMLLoader(SisFranceApp.class.getResource(fxmlFileName));
        Parent root = fxmlLoader.load();
        SisFranceView view = fxmlLoader.getController();
        SisFranceViewModel viewModel = new SisFranceViewModel();
        view.setViewModel(viewModel);
        SisFranceModel model = new SisFranceModel();
        viewModel.setModel(model);


        view.setFranceApp(SisFranceApp.class.newInstance());

        Scene scene = new Scene(root,1200, 800);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}