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
    private String xRgf93L93;
    private String yRgf93L93;
    @FXML
    private Pane graphPane; // Pane pour afficher le graphique

    @FXML
    private MenuItem graph1;
    @FXML
    private MenuItem graph2;
    @FXML
    private MenuItem graph3;

    private static ArrayList<SisFranceModel> donneesSismiques = new ArrayList<>();
    private String identifiant;
    private String date;
    private String heure;
    private String nom;
    private String region;
    private String choc;
    private String longitudeWGS84; // Nouveau nom de la colonne de longitude en WGS84
    private String latitudeWGS84; // Nouveau nom de la colonne de latitude en WGS84
    private String intensite;
    private String qualite;

    public SisFranceModel(String identifiant, String date, String heure, String nom, String region, String choc,
                          String xRgf93L93, String yRgf93L93, String latitudeWGS84, String longitudeWGS84,
                          String intensite, String qualite) {
        this.identifiant = identifiant;
        this.date = date;
        this.heure = heure;
        this.nom = nom;
        this.region = region;
        this.choc = choc;
        this.xRgf93L93 = xRgf93L93;
        this.yRgf93L93 = yRgf93L93;
        this.latitudeWGS84 = latitudeWGS84;
        this.longitudeWGS84 = longitudeWGS84;
        this.intensite = intensite;
        this.qualite = qualite;
    }


    public SisFranceModel() {

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

    public String getLongitudeWGS84() {
        return longitudeWGS84;
    }

    public void setLongitudeWGS84(String longitudeWGS84) {
        this.longitudeWGS84 = longitudeWGS84;
    }

    public String getLatitudeWGS84() {
        return latitudeWGS84;
    }

    public void setLatitudeWGS84(String latitudeWGS84) {
        this.latitudeWGS84 = latitudeWGS84;
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

    public ArrayList<SisFranceModel> getDonneesSismiques() {
        return donneesSismiques;
    }

    public static void openCsv() {
        Stage stage = new Stage();
        stage.setTitle("Visionneur de CSV");

        TableView<ObservableList<String>> tableView = new TableView<>();
        TextField searchField = new TextField(); // Champ de recherche
        searchField.setPromptText("Rechercher des mots-clés, des identifiants, des dates...");

        // Supposons que vos données CSV aient 12 colonnes
        String[] columnNames = {"Identifiant", "Date (AAAA/MM/JJ)", "Heure", "Nom", "Région épicentrale", "Choc",
                "X RGF93/L93", "Y RGF93/L93", "Latitude en WGS 84", "Longitude en WGS 84", "Intensité épicentrale", "Qualité intensité épicentrale"};

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
            rowData.add(row.xRgf93L93); // Ajout du champ X RGF93/L93
            rowData.add(row.yRgf93L93); // Ajout du champ Y RGF93/L93
            rowData.add(row.latitudeWGS84);
            rowData.add(row.longitudeWGS84);
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
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine(); // ignore header
                ArrayList<SisFranceModel> nouvellesDonneesSismiques = new ArrayList<>(); // Liste temporaire pour les nouvelles données
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 12) {
                        SisFranceModel ds = new SisFranceModel(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                                parts[6], parts[7], parts[8], parts[9], parts[10], parts[11]);
                        nouvellesDonneesSismiques.add(ds);
                    }
                }

                // Mettre à jour les données sismiques et actualiser la couche de la carte
                donneesSismiques.clear();
                donneesSismiques.addAll(nouvellesDonneesSismiques);
                SisFranceApp.mapLayer.updateLayer(donneesSismiques);
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
                bw.write("\"Identifiant\",\"Date (AAAA/MM/JJ)\",\"Heure\",\"Nom\",\"Région épicentrale\",\"Choc\",\"X RGF93/L93\",\"Y RGF93/L93\",\"Latitude en WGS 84\",\"Longitude en WGS 84\",\"Intensité épicentrale\",\"Qualité intensité épicentrale\"\n");
                for (SisFranceModel ds : donneesSismiques) {
                    bw.write("\"" + ds.identifiant + "\",\"" + ds.date + "\",\"" + ds.heure + "\",\"" + ds.nom + "\",\"" + ds.region +
                            "\",\"" + ds.choc + "\",\"" + ds.longitudeWGS84 + "\",\"" + ds.latitudeWGS84 +
                            "\",\"" + ds.intensite + "\",\"" + ds.qualite + "\"\n");
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
