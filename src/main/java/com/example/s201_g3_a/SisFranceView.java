package com.example.s201_g3_a;


import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.*;
import java.util.*;


public class SisFranceView {

    private SisFranceViewModel viewModel;


    @FXML
    private MenuItem importer;
    @FXML
    private MenuItem exporter;

    @FXML
    private MenuItem openCsv;






    @FXML
    private Menu carte;

    @FXML
    private MapView map;
    @FXML
    private static MapPoint pointFr = new MapPoint(46,2);
    @FXML
    private Pane paneMG;

    public static CustomMapLayer mapLayer ;
    private List<SisFranceModel> seisme = new ArrayList<SisFranceModel>();

    private SisFranceApp MainApp;

    public void setFranceApp(SisFranceApp MainApp) {
        this.MainApp = MainApp;
    }

    public void setViewModel(SisFranceViewModel viewModel) {
        this.viewModel = viewModel;

    }



    @FXML
    private void initialize()
    {
        importer.setOnAction(event -> SisFranceModel.importerDonneesCsv());
        exporter.setOnAction(event -> SisFranceModel.exporterDonneesCsv());
        openCsv.setOnAction(event -> SisFranceModel.openCsv());


        if(map != null ) {
            mapLayer=new CustomMapLayer(seisme);
            mapLayer.updateLayer();
            map.flyTo(0,pointFr,0.1);
            map.addLayer(mapLayer);

        }
        else {
            System.out.println("");
        }

    }

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

}
