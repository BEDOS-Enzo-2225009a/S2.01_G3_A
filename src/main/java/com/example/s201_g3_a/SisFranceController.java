package com.example.s201_g3_a;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SisFranceController {

    @FXML
    private Menu carte;
    @FXML
    private MenuItem graph1;
    @FXML
    private MenuItem graph2;
    @FXML
    private MenuItem graph3;



    @FXML
    private void onCarteMenuchange(){
        graph1.setOnAction(event -> {
            if (graph1.getText()=="Carte"){
                carte.setText("Carte");
                graph1.setText("Graphique 1");
            }
            else {
                carte.setText("Graphique 1");
                graph1.setText("Carte");
                graph2.setText("Graphique 2");
                graph3.setText("Graphique 3");
            }
        });
        graph2.setOnAction(event -> {
            if (graph2.getText()=="Carte"){
                carte.setText("Carte");
                graph2.setText("Graphique 2");
            }
            else {
            carte.setText("Graphique 2");
            graph2.setText("Carte");
            graph1.setText("Graphique 1");
            graph3.setText("Graphique 3");
            }
        });
        graph3.setOnAction(event -> {
            if (graph3.getText()=="Carte"){
                carte.setText("Carte");
                graph3.setText("Graphique 3");
            }
            else {
                carte.setText("Graphique 3");
                graph3.setText("Carte");
                graph1.setText("Graphique 1");
                graph2.setText("Graphique 2");
            }
        });

    }
}