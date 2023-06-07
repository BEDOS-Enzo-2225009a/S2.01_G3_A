package com.example.s201_g3_a;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class SisFranceView {

    private SisFranceViewModel viewModel;

    @FXML
    private MenuItem importer;
    @FXML
    private MenuItem exporter;

    @FXML
    private MenuItem openCsv;


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
    private WebView map;

    private List<DonneesSismiques> donneesSismiques = new ArrayList<>();

    private class DonneesSismiques {
        String identifiant;
        String date;
        String heure;
        String intensite;
        String qualite;
        String nom;
        String region;
        String choc;

        DonneesSismiques(String identifiant, String date, String heure, String intensite, String qualite, String nom, String region, String choc) {
            this.identifiant = identifiant;
            this.date = date;
            this.heure = heure;
            this.intensite = intensite;
            this.qualite = qualite;
            this.nom = nom;
            this.region = region;
            this.choc = choc;
        }
    }




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


        importer.setOnAction(event -> importerDonneesCsv());
        exporter.setOnAction(event -> exporterDonneesCsv());
        openCsv.setOnAction(event -> openCsv());
    }

    private void openCsv() {
        Stage stage = new Stage();
        stage.setTitle("Visionneur de CSV");

        TableView<ObservableList<String>> tableView = new TableView<>();
        TextField searchField = new TextField(); // Champ de recherche
        searchField.setPromptText("Rechercher des mots-clés, des identifiants, des dates...");

        // Supposons que vos données CSV aient 8 colonnes
        String[] columnNames = {"Identifiant", "Date (AAAA/MM/JJ)", "Heure", "Intensité épicentrale",
                "Qualité intensité épicentrale", "Nom", "Région épicentrale", "Choc"};

        for (int i = 0; i < columnNames.length; i++) {
            final int columnIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
            column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(columnIndex)));
            tableView.getColumns().add(column);
        }

        // Ajoutez les données à la TableView
        for (DonneesSismiques row : donneesSismiques) {
            ObservableList<String> rowData = FXCollections.observableArrayList();
            rowData.add(row.identifiant);
            rowData.add(row.date);
            rowData.add(row.heure);
            rowData.add(row.intensite);
            rowData.add(row.qualite);
            rowData.add(row.nom);
            rowData.add(row.region);
            rowData.add(row.choc);
            tableView.getItems().add(rowData);
        }

        // Créez une FilteredList pour filtrer les données en fonction du champ de recherche
        FilteredList<ObservableList<String>> filteredData = new FilteredList<>(tableView.getItems(), p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(rowData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Afficher toutes les données lorsque le champ de recherche est vide
                }

                String keyword = newValue.toLowerCase();

                // Vérifiez si chaque cellule contient le mot-clé de recherche
                for (String cellValue : rowData) {
                    if (cellValue.toLowerCase().contains(keyword)) {
                        return true;
                    }
                }

                return false; // Masquer la ligne si aucun mot-clé n'est trouvé
            });
        });

        // Appliquez le filtre à la TableView
        tableView.setItems(filteredData);

        // Ajoutez la TableView et le champ de recherche à une VBox
        VBox vbox = new VBox(searchField, tableView);

        // Ajoutez la VBox à la nouvelle fenêtre et affichez-la
        stage.setScene(new Scene(vbox));
        stage.show();
    }







    private void importerDonneesCsv() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        fileChooser.setTitle("Ouvrir fichier CSV");

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                br.readLine(); // ignore header
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 9) {
                        DonneesSismiques ds = new DonneesSismiques(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
                        donneesSismiques.add(ds);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void exporterDonneesCsv() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        fileChooser.setTitle("Sauvegarder fichier CSV");

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write("\"\",\"Identifiant\",\"Date (AAAA/MM/JJ)\",\"Heure\",\"Intensité épicentrale\",\"Qualité intensité épicentrale\",\"Nom\",\"Région épicentrale\",\"Choc\"\n");
                for (DonneesSismiques ds : donneesSismiques) {
                    bw.write("\"\"" + "," + ds.identifiant + "," + ds.date + "," + ds.heure + "," + ds.intensite + "," + ds.qualite + "," + ds.nom + "," + ds.region + "," + ds.choc + "\n");
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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