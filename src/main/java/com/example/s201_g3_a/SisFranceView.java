package com.example.s201_g3_a;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SisFranceView {

    private SisFranceViewModel viewModel;

    @FXML
    private Pane graphPane; // Pane pour afficher le graphique



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

    @FXML
    private Pane paneMG;

    @FXML
    private ChoiceBox<String> regionChoiceBox;
    @FXML
    private ChoiceBox<Integer> deDay;
    @FXML
    private ChoiceBox<Integer> aDay;
    @FXML
    private ChoiceBox<Integer> deMonth;
    @FXML
    private ChoiceBox<Integer> aMonth;
    @FXML
    private ChoiceBox<Integer> deYear;
    @FXML
    private ChoiceBox<Integer> aYear;

    private List<DonneesSismiques> donneesSismiques = new ArrayList<>();

    private class DonneesSismiques { // Classe que j'ai créé pour pourvoir classer les données. Si vous voulez réutiliser les données importées par l'import d'un fichier CSV, il faut utiliser la liste
        // donneesSismiques que j'ai créée juste en haut. Elle contient après l'import tout ce qu'il faut pour utiliser les données du CSV.
        private String identifiant;
        private String date;
        private String heure;
        private String intensite;
        private String qualite;
        private String nom;
        private String region;
        private String choc;



        public DonneesSismiques(String identifiant, String date, String heure, String intensite, String qualite, String nom, String region, String choc) {
            this.identifiant = identifiant;
            this.date = date;
            this.heure = heure;
            this.intensite = intensite;
            this.qualite = qualite;
            this.nom = nom;
            this.region = region;
            this.choc = choc;
        }

        public String getIdentifiant() {
            return identifiant;
        }

        public void setIdentifiant(String identifiant) {
            this.identifiant = identifiant;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHeure() {
            return heure;
        }

        public void setHeure(String heure) {
            this.heure = heure;
        }

        public String getIntensite() {
            return intensite;
        }

        public void setIntensite(String intensite) {
            this.intensite = intensite;
        }

        public String getQualite() {
            return qualite;
        }

        public void setQualite(String qualite) {
            this.qualite = qualite;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getChoc() {
            return choc;
        }

        public void setChoc(String choc) {
            this.choc = choc;
        }
    }





    @FXML
    private void initialize()
    {
        WebEngine engine = map.getEngine();
        engine.loadContent(
                "<!DOCTYPE html>\n"
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

    private void afficherGraphique1(List<DonneesSismiques> donnees) {
        // Création des axes du graphique
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Configuration de l'axe des abscisses
        xAxis.setLabel("Année");

        // Création du graphique en barres
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Configuration du graphique
        barChart.setTitle("Intensité des données sismiques");

        // Création de la série de données
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Intensité");

        // Ajout des données à la série
        for (DonneesSismiques donnee : donnees) {
            String year = donnee.getDate().substring(0, 4); // Récupération des quatre premiers caractères pour l'année
            double intensity = Double.parseDouble(donnee.getIntensite().replace("\"", ""));

            dataSeries.getData().add(new XYChart.Data<>(year, intensity));
        }

        // Ajout de la série au graphique
        barChart.getData().add(dataSeries);

        // Ajout du graphique à la zone graphPane
        graphPane.getChildren().clear();
        graphPane.getChildren().add(barChart);
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
        getCsvInfoFXML();
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
    private void onCarteMenuchange() {
        if (carte.getText().equals("Graphique 1")) {
            carte.setText("Carte");
            graph1.setText("Graphique 1");
            graphPane.getChildren().clear(); // Efface le graphique précédent
            map.setVisible(true); // Affiche la carte
        } else if (carte.getText().equals("Graphique 2")) {
            carte.setText("Carte");
            graph2.setText("Graphique 2");
            graphPane.getChildren().clear(); // Efface le graphique précédent
            map.setVisible(true); // Affiche la carte
        } else if (carte.getText().equals("Graphique 3")) {
            carte.setText("Carte");
            graph3.setText("Graphique 3");
            graphPane.getChildren().clear(); // Efface le graphique précédent
            map.setVisible(true); // Affiche la carte
        } else {
            carte.setText("Graphique 1");
            graph1.setText("Carte");
            graph2.setText("Graphique 2");
            graph3.setText("Graphique 3");
            graphPane.getChildren().clear(); // Efface le graphique précédent
            map.setVisible(false); // Cache la carte
            afficherGraphique1(donneesSismiques); // Affiche le graphique 1 en utilisant les données pertinentes

        }
    }

    private ArrayList<Integer> dateDayMonth(int quantite) {
        ArrayList<Integer> qdate = new ArrayList<>();

        for(int i = 1; i < quantite + 1; ++i){
            qdate.add(i);
        }
        return qdate;
    }

    private ArrayList<Integer> dateYear(int from, int to) {
        ArrayList<Integer> qdate = new ArrayList<>();
        int i = from;

        while (i < to + 1) {
            qdate.add(i);
            ++i;
        }
        return qdate;
    }


    @FXML
    private void getCsvInfoFXML() {

        // Regions
        ArrayList<String> regions = new ArrayList<>();

        for (DonneesSismiques row : donneesSismiques) {
            String region = row.region;
            if (!regions.contains(region)) {
                regions.add(region);
            }
        }

        // Ajout des ChoiceBox
        regionChoiceBox.getItems().addAll(regions);
        deDay.getItems().addAll(dateDayMonth(31));
        aDay.getItems().addAll(dateDayMonth(31));
        deMonth.getItems().addAll(dateDayMonth(12));
        aMonth.getItems().addAll(dateDayMonth(12));
        deYear.getItems().addAll(dateYear(1900, 2050));
        aYear.getItems().addAll(dateYear(1900, 2050));
    }



}


