package com.example.s201_g3_a;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class SisFranceView {

    private SisFranceViewModel viewModel;

    public void setViewModel(SisFranceViewModel viewModel) {
        this.viewModel = viewModel;

    }


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