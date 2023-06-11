package com.example.s201_g3_a;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SisFranceModel {
    @FXML
    private Pane graphPane; // Pane pour afficher le graphique


    @FXML
    private MenuItem graph1;
    @FXML
    private MenuItem graph2;
    @FXML
    private MenuItem graph3;

    private static List<SisFranceModel> donneesSismiques = new ArrayList<>();
    private String identifiant;
    private String date;
    private String heure;
    private String nom;
    private String region;
    private String choc;
    private String longitude;
    private String latitude;
    private String intensite;
    private String qualite;

    public SisFranceModel(String identifiant, String date, String heure, String nom, String region, String choc,
                          String longitude, String latitude, String intensite, String qualite) {
        this.identifiant = identifiant;
        this.date = date;
        this.heure = heure;
        this.nom = nom;
        this.region = region;
        this.choc = choc;
        this.longitude = longitude;
        this.latitude = latitude;
        this.intensite = intensite;
        this.qualite = qualite;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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




    public static void openCsv() {
        Stage stage = new Stage();
        stage.setTitle("Visionneur de CSV");

        TableView<ObservableList<String>> tableView = new TableView<>();
        TextField searchField = new TextField(); // Champ de recherche
        searchField.setPromptText("Rechercher des mots-clés, des identifiants, des dates...");

        // Supposons que vos données CSV aient 10 colonnes
        String[] columnNames = {"Identifiant", "Date (AAAA/MM/JJ)", "Heure", "Nom", "Région épicentrale", "Choc",
                "Longitude", "Latitude", "Intensité épicentrale", "Qualité intensité épicentrale"};

        for (int i = 0; i < columnNames.length; i++) {
            final int columnIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
            column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(columnIndex)));
            tableView.getColumns().add(column);
        }

        // Ajoutez les données à la TableView
        for (SisFranceModel row : donneesSismiques) {
            ObservableList<String> rowData = FXCollections.observableArrayList();
            rowData.add(row.identifiant);
            rowData.add(row.date);
            rowData.add(row.heure);
            rowData.add(row.nom);
            rowData.add(row.region);
            rowData.add(row.choc);
            rowData.add(row.longitude);
            rowData.add(row.latitude);
            rowData.add(row.intensite);
            rowData.add(row.qualite);
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

    public static void importerDonneesCsv() {
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
                    if (parts.length >= 10) {
                        SisFranceModel ds = new SisFranceModel(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                                parts[6], parts[7], parts[8], parts[9]);
                        donneesSismiques.add(ds);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exporterDonneesCsv() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        fileChooser.setTitle("Sauvegarder fichier CSV");

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write("\"Identifiant\",\"Date (AAAA/MM/JJ)\",\"Heure\",\"Nom\",\"Région épicentrale\",\"Choc\",\"Longitude\",\"Latitude\",\"Intensité épicentrale\",\"Qualité intensité épicentrale\"\n");
                for (SisFranceModel ds : donneesSismiques) {
                    bw.write("\"" + ds.identifiant + "\",\"" + ds.date + "\",\"" + ds.heure + "\",\"" + ds.nom + "\",\"" + ds.region +
                            "\",\"" + ds.choc + "\",\"" + ds.longitude + "\",\"" + ds.latitude +
                            "\",\"" + ds.intensite + "\",\"" + ds.qualite + "\"\n");
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
