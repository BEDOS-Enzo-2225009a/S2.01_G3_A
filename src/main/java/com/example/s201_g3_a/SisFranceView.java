package com.example.s201_g3_a;


import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.*;
import java.util.*;




/**

 Classe de vue pour l'application SisFrance.

 Cette classe gère l'interface utilisateur et les interactions avec l'utilisateur.

 La classe contient des éléments de l'interface utilisateur définis à l'aide de JavaFX,

 tels que des menus, des choix, des cartes, des graphiques, etc.

 Elle communique avec le modèle de données (SisFranceModel) et le modèle de vue (SisFranceViewModel)

 pour effectuer des opérations liées à l'affichage des données sismiques et aux interactions utilisateur.

 La classe utilise des annotations JavaFX (FXML) pour lier les éléments de l'interface utilisateur

 aux méthodes correspondantes dans la classe.

 Exemple d'utilisation :

 SisFranceViewModel viewModel = new SisFranceViewModel();

 SisFranceView view = new SisFranceView();

 view.setViewModel(viewModel);

 @see SisFranceModel

 @see SisFranceViewModel
 */
public class SisFranceView {

    private SisFranceViewModel viewModel;


    @FXML
    private MenuItem importer;
    @FXML
    private MenuItem exporter;

    @FXML
    private MenuItem openCsv;


    /**

     Définit le modèle de vue associé à cette vue.
     @param viewModel Le modèle de vue à associer
     */
    public void setViewModel(SisFranceViewModel viewModel) {
        this.viewModel = viewModel;

    }
    @FXML
    private MapView map;


    @FXML
    private static MapPoint pointFr = new MapPoint(46, 2);

    public static CustomMapLayer mapLayer;
    private List<SisFranceModel> seisme = new ArrayList<SisFranceModel>();

    @FXML
    private ChoiceBox<String> regionChoiceBox;
    @FXML
    private ChoiceBox<Integer> fromDate;
    @FXML
    private ChoiceBox<Integer> toDate;


    @FXML
    private TitledPane filterPane;


    /**

     Méthode d'initialisation appelée après que les éléments de l'interface utilisateur ont été chargés.

     Cette méthode configure les actions des menus, la carte, les filtres, etc.
     */
    @FXML
    private void initialize() {
        importer.setOnAction(event -> importerDonneesCsv());
        exporter.setOnAction(event -> SisFranceModel.exporterDonneesCsv());
        openCsv.setOnAction(event -> SisFranceModel.openCsv());

        map.setMinHeight(800);
//        map.setMinWidth(1100);
        filterPane.toFront();
        filterPane.setCollapsible(false);


        if (map != null) {
            mapLayer = new CustomMapLayer(seisme);
            mapLayer.updateLayer();
            map.flyTo(0, pointFr, 0.1);
            map.addLayer(mapLayer);

        } else {
            System.out.println("");
        }



    }


    /**

     Affiche un graphique montrant les intensités sismiques par région.

     Le graphique est créé en utilisant les données du modèle SisFranceModel.

     Les régions et les intensités sont extraits des données et utilisés pour créer un graphique à barres.

     Le graphique est affiché dans une nouvelle fenêtre.

     @throws IOException En cas d'erreur lors de la création de la fenêtre du graphique
     */
    @FXML
    private void goToGraph1() throws IOException {
        // Extract the relevant data for the graph
        List<Double> intensities = new ArrayList<>();
        List<String> regions = new ArrayList<>();

        for (SisFranceModel data : SisFranceModel.getDonneesSismiques()) {
            if (data.getIntensite() != null && !data.getIntensite().isEmpty()) {
                try {
                    double intensity = Double.parseDouble(data.getIntensite());
                    intensities.add(intensity);

                    // Remove unwanted characters at the beginning and end of region names
                    String region = data.getRegion().trim().replaceAll("^\\W+|\\W+$", "");
                    regions.add(region);
                } catch (NumberFormatException e) {
                    // Handle non-numeric values in the intensite field
                    System.err.println("Invalid intensity value: " + data.getIntensite());
                }
            }
        }

        // Create the bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Adjust chart properties for better readability
        barChart.setPrefSize(800, 600); // Set the preferred size of the chart
        xAxis.setLabel("Régions");
        yAxis.setLabel("Intensité");
        xAxis.setTickLabelFont(Font.font("Arial", 12));
        yAxis.setTickLabelFont(Font.font("Arial", 12));
        yAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return String.valueOf(object.doubleValue());
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        });

        // Add data to the bar chart
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (int i = 0; i < intensities.size(); i++) {
            dataSeries.getData().add(new XYChart.Data<>(regions.get(i), intensities.get(i)));
        }
        barChart.getData().add(dataSeries);

        // Create the scene and stage for the graph view
        Parent root = new VBox(barChart);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Graphique des intensités sismiques par région");
        stage.show();
    }

    /**

     Affiche un graphique montrant la répartition des séismes dans les 10 régions les plus touchées.

     Le graphique est créé en utilisant les données du modèle SisFranceModel.

     Les régions et le nombre d'occurrences de chaque région sont comptés à partir des données.

     Les régions les plus touchées sont sélectionnées et utilisées pour créer un graphique en secteurs.

     Le graphique est affiché dans une nouvelle fenêtre.

     @throws IOException En cas d'erreur lors de la création de la fenêtre du graphique
     */
    @FXML
    private void goToGraph2() throws IOException {
        // Exemple de données sélectionnées
        List<String> regions = new ArrayList<>();
        List<Integer> occurrences = new ArrayList<>();

        // Compter le nombre d'occurrences de chaque région
        Map<String, Integer> regionOccurrences = new HashMap<>();
        for (SisFranceModel data : SisFranceModel.getDonneesSismiques()) {
            String region = data.getRegion();
            region = region.replaceAll("\"", ""); // Supprimer les guillemets indésirables
            regionOccurrences.put(region, regionOccurrences.getOrDefault(region, 0) + 1);
        }

        // Trier les régions par nombre d'occurrences décroissant
        List<Map.Entry<String, Integer>> sortedRegions = new ArrayList<>(regionOccurrences.entrySet());
        sortedRegions.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Calculer le total des occurrences
        int totalOccurrences = 0;
        for (Map.Entry<String, Integer> entry : sortedRegions) {
            totalOccurrences += entry.getValue();
        }

        // Sélectionner les régions avec le plus de séismes (par exemple, les 5 premières)
        int maxRegions = 5;
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedRegions) {
            if (count >= maxRegions) {
                break;
            }
            regions.add(entry.getKey());
            occurrences.add(entry.getValue());
            count++;
        }

        // Calculer le pourcentage de chaque région
        List<Double> percentages = new ArrayList<>();
        for (int i = 0; i < occurrences.size(); i++) {
            double percentage = (double) occurrences.get(i) / totalOccurrences * 100;
            percentages.add(percentage);
        }

        // Create the pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < regions.size(); i++) {
            String region = regions.get(i);
            int occurrence = occurrences.get(i);
            double percentage = percentages.get(i);
            pieChartData.add(new PieChart.Data(region + " (" + String.format("%.1f", percentage) + "%)", occurrence));
        }
        PieChart pieChart = new PieChart(pieChartData);

        // Adjust chart properties for better readability
        pieChart.setPrefSize(800, 600); // Augmenter la taille du graphique
        pieChart.setTitle("Répartition des séismes dans les 10 régions les plus touchées");

        // Create the scene and stage for the graph view
        Parent root = new VBox(pieChart);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**

     Importe les données à partir d'un fichier CSV.

     Le fichier CSV est sélectionné à l'aide d'un dialogue de sélection de fichiers.

     Une fois le fichier sélectionné, les données sont chargées dans le modèle SisFranceModel.

     Les régions et les dates des séismes sont extraites des données et utilisées pour mettre à jour les ChoiceBox correspondantes.
     */
    @FXML
    private void importerDonneesCsv() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        fileChooser.setTitle("Ouvrir fichier CSV");

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            SisFranceModel.chargerDonneesCsv(file);

            // Récupérer les régions et les dates
            ArrayList<String> regions = new ArrayList<>();
            ArrayList<Integer> dates = new ArrayList<>();

            for (SisFranceModel row : SisFranceModel.getDonneesSismiques()) {
                String region = row.getRegion();
                if (!regions.contains(region)) {
                    regions.add(region);
                }
                int year = Integer.parseInt(row.getDate().substring(0, 4));
                if (!dates.contains(year)) {
                    dates.add(year);
                }
            }


            // Mettre à jour les ChoiceBox
            regionChoiceBox.getItems().setAll(regions);
            fromDate.getItems().setAll(dates);
            toDate.getItems().setAll(dates);
        }
    }


}
