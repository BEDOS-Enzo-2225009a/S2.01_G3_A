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

import static com.example.s201_g3_a.SisFranceView.mapLayer;


/**
 * Cette classe représente un modèle de données pour les séismes en France.
 * Elle contient les informations associées à chaque séisme, telles que l'identifiant, la date, l'heure, le nom,
 * la région épicentrale, le choc, les coordonnées, l'intensité et la qualité.
 * La classe fournit également des méthodes pour afficher les données dans une TableView, exporter les données au format CSV
 * et charger les données à partir d'un fichier CSV.
 */
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


    /**
     * Liste des données sismiques.
     */
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

    /**
     * Constructeur de la classe SisFranceModel.
     *
     * @param identifiant     L'identifiant du séisme.
     * @param date            La date du séisme.
     * @param heure           L'heure du séisme.
     * @param nom             Le nom du séisme.
     * @param region          La région épicentrale du séisme.
     * @param choc            Le choc du séisme.
     * @param xRgf93L93       La coordonnée X RGF93/L93 du séisme.
     * @param yRgf93L93       La coordonnée Y RGF93/L93 du séisme.
     * @param latitudeWGS84   La latitude en WGS 84 du séisme.
     * @param longitudeWGS84  La longitude en WGS 84 du séisme.
     * @param intensite       L'intensité épicentrale du séisme.
     * @param qualite         La qualité de l'intensité épicentrale du séisme.
     */
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


    /**
     * Méthode statique pour définir la liste des données sismiques.
     *
     * @param donneesSismiques La liste des données sismiques.
     */
    public static void setDonneesSismiques(ArrayList<SisFranceModel> donneesSismiques) {
        SisFranceModel.donneesSismiques = donneesSismiques;
    }

    /**
     * Méthode statique pour obtenir la liste des données sismiques.
     *
     * @return La liste des données sismiques.
     */
    public static ArrayList<SisFranceModel> getDonneesSismiques(ArrayList<SisFranceModel> donneesSismiques) {
        return donneesSismiques;
    }

    /**

     Retourne la région du séisme.
     @return La région du séisme.
     */
    public String getRegion() {
        return region;
    }

    /**

     Retourne la longitude WGS84 du séisme.
     @return La longitude WGS84 du séisme.
     */
    public String getLongitudeWGS84() {
        return longitudeWGS84;
    }

    /**

     Retourne la latitude WGS84 du séisme.
     @return La latitude WGS84 du séisme.
     */
    public String getLatitudeWGS84() {
        return latitudeWGS84;
    }

    /**

     Retourne la date du séisme.
     @return La date du séisme.
     */
    public String getDate() {
        return date;
    }

    /**

     Retourne l'intensité du séisme.
     @return L'intensité du séisme.
     */
    public String getIntensite() {
        return intensite;
    }
    public static ArrayList<SisFranceModel> getDonneesSismiques() {
        return donneesSismiques;
    }



    /**

     Ouvre un fichier CSV dans une fenêtre de visionneur.
     Affiche les données CSV dans une TableView avec fonctionnalités de recherche.
     Cette méthode crée une nouvelle fenêtre de visionneur de CSV, où les données CSV sont affichées dans une TableView.
     La méthode utilise JavaFX pour créer et afficher l'interface utilisateur.
     L'utilisateur peut rechercher des mots-clés, des identifiants ou des dates en utilisant un champ de recherche.
     Les données sont filtrées en fonction des mots-clés saisis, et seules les lignes contenant des valeurs correspondantes
     sont affichées dans la TableView.
     La méthode suppose que les données CSV ont 12 colonnes avec les noms suivants :
     "Identifiant", "Date (AAAA/MM/JJ)", "Heure", "Nom", "Région épicentrale", "Choc",
     "X RGF93/L93", "Y RGF93/L93", "Latitude en WGS 84", "Longitude en WGS 84",
     "Intensité épicentrale", "Qualité intensité épicentrale".
     Les données CSV sont fournies sous forme d'une liste d'objets SisFranceModel, où chaque objet représente une ligne
     du fichier CSV.
     Exemple d'utilisation :
     openCsv();
     Cette méthode ne retourne aucune valeur et ne lance aucune exception.
     Elle affiche simplement la fenêtre du visionneur de CSV.
     */
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


    /**
     * Méthode pour exporter les données sismiques au format CSV.
     */
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


    /**

     Charge les données d'un fichier CSV dans une liste de modèles de séismes.
     Cette méthode lit les données d'un fichier CSV spécifié et les convertit en objets SisFranceModel.
     Les objets SisFranceModel représentent chaque ligne du fichier CSV et contiennent les valeurs correspondantes.
     Les données converties sont ensuite stockées dans une liste de modèles de séismes.
     Le fichier CSV doit être passé en tant que paramètre de type File.
     La méthode vérifie d'abord si le fichier est non nul avant de commencer le chargement des données.
     Exemple d'utilisation :
     File fichierCsv = new File("chemin/vers/fichier.csv");
     chargerDonneesCsv(fichierCsv);
     @param file Le fichier CSV à charger
     Cette méthode ne retourne aucune valeur, mais met à jour la liste de modèles de séismes (donneesSismiques) et
     la couche de carte associée (mapLayer).
     En cas d'erreur de lecture du fichier ou d'exception d'entrée/sortie, une trace de la pile est affichée.
     */

    public static void chargerDonneesCsv(File file) {
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

                donneesSismiques.clear();
                donneesSismiques.addAll(nouvellesDonneesSismiques);
                mapLayer.setListeSeismes(donneesSismiques);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}