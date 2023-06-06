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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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
    private WebView map;


    @FXML
    private void initialize()
    {
        WebEngine engine = map.getEngine();
        engine.loadContent("<!DOCTYPE html>\n"
                + "<html lang=\"fr\">\n"
                + "\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Carte</title>\n"
                + "    <!-- leafletjs CSS -->\n"
                + "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/leaflet.min.css\" />\n"
                + "    <!-- leafletjs JS -->\n"
                + "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/leaflet.min.js\"></script>\n"
                + "    <style>\n"
                + "        /* Carte plein écran */\n"
                + "        html,\n"
                + "        body {\n"
                + "            margin: 0;\n"
                + "            height: 100%;\n"
                + "        }\n"
                + "\n"
                + "        #map {\n"
                + "            width: 100%;\n"
                + "            height: 100%;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "\n"
                + "<body>\n"
                + "\n"
                + "    <!-- L'endroit ou la carte va s'afficher -->\n"
                + "    <div id=\"map\"></div>\n"
                + "\n"
                + "    <script>\n"
                + "        /* Les options pour afficher la France */\n"
                + "        const mapOptions = {\n"
                + "            center: [46.225, 0.132],\n"
                + "            zoom: 6\n"
                + "        }\n"
                + "\n"
                + "        /* Les options pour affiner la localisation */\n"
                + "        const locationOptions = {\n"
                + "            maximumAge: 10000,\n"
                + "            timeout: 5000,\n"
                + "            enableHighAccuracy: true\n"
                + "        };\n"
                + "\n"
                + "        /* Création de la carte */\n"
                + "        var map = new L.map(\"map\", mapOptions);\n"
                + "\n"
                + "        /* Création de la couche OpenStreetMap */\n"
                + "        var layer = new L.TileLayer(\"http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png\",\n"
                + "            { attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors' });\n"
                + "\n"
                + "        /* Ajoute la couche de la carte */\n"
                + "        map.addLayer(layer);\n"
                + "\n"
                + "        /* Verifie que le navigateur est compatible avec la géolocalisation */\n"
                + "        if (\"geolocation\" in navigator) {\n"
                + "            navigator.geolocation.getCurrentPosition(handleLocation, handleLocationError, locationOptions);\n"
                + "        } else {\n"
                + "            /* Le navigateur n'est pas compatible */\n"
                + "            alert(\"Géolocalisation indisponible\");\n"
                + "        }\n"
                + "\n"
                + "        function handleLocation(position) {\n"
                + "            /* Zoom avant de trouver la localisation */\n"
                + "            map.setZoom(16);\n"
                + "            /* Centre la carte sur la latitude et la longitude de la localisation de l'utilisateur */\n"
                + "            map.panTo(new L.LatLng(position.coords.latitude, position.coords.longitude));\n"
                + "        }\n"
                + "\n"
                + "        function handleLocationError(msg) {\n"
                + "            alert(\"Erreur lors de la géolocalisation\");\n"
                + "        }\n"
                + "\n"
                + "    </script>\n"
                + "\n"
                + "</body>\n"
                + "\n"
                + "</html>");

    }
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