package com.example.s201_g3_a;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SisFranceController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}