package com.example.s201_g3_a;


import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
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
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private MapView map;
    @FXML
    private static MapPoint pointFr = new MapPoint(46,2);
    @FXML
    private Pane paneMG;

    public static CustomMapLayer mapLayer ;
    private List<SisFranceModel> seisme = new ArrayList<SisFranceModel>();

    @FXML
    private ChoiceBox<String> regionChoiceBox;
    @FXML
    private ChoiceBox<Integer> fromDate;
    @FXML
    private ChoiceBox<Integer> toDate;



    @FXML
    private void initialize()
    {
        importer.setOnAction(event -> importerDonneesCsv());
        exporter.setOnAction(event -> SisFranceModel.exporterDonneesCsv());
        openCsv.setOnAction(event -> SisFranceModel.openCsv());


        mapLayer=new CustomMapLayer(seisme);
        mapLayer.updateLayer();
        map.flyTo(0,pointFr,0.1);
        map.addLayer(mapLayer);

    }
    private ArrayList<Integer> getFirstLastDate(){
        ArrayList<Integer> dates = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int actualYear = c.get(Calendar.YEAR);

        for(int i = 1600; i < actualYear + 1; ++i){
            dates.add(i);
        }
        return dates;
    }
    @FXML
    private void getCsvInfoFXML() {

        // Regions
        ArrayList<String> regions = new ArrayList<>();

        for (SisFranceModel row : SisFranceModel.getDonneesSismiques()) {
            String region = row.getRegion();
            if (!regions.contains(region)) {
                regions.add(region);
            }
        }
        // Dates
        ArrayList<Integer> dates = getFirstLastDate();

        // Ajout des ChoiceBox
        regionChoiceBox.getItems().addAll(regions);
        fromDate.getItems().addAll(dates);
        toDate.getItems().addAll(dates);

    }

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
