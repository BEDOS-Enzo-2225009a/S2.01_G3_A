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
import java.util.ArrayList;
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
    private void initialize()
    {
        importer.setOnAction(event -> SisFranceModel.importerDonneesCsv());
        exporter.setOnAction(event -> SisFranceModel.exporterDonneesCsv());
        openCsv.setOnAction(event -> SisFranceModel.openCsv());

        mapLayer=new CustomMapLayer(seisme);
        mapLayer.updateLayer();
        map.flyTo(0,pointFr,0.1);
        map.addLayer(mapLayer);

    }

}
