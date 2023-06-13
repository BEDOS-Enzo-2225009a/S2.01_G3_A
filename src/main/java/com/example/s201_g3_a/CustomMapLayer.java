package com.example.s201_g3_a;



import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.example.s201_g3_a.SisFranceView;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class CustomMapLayer extends MapLayer {

    private List<SisFranceModel> listeSeismes;

    public CustomMapLayer(List<SisFranceModel> listeSeismes){
        this.listeSeismes=listeSeismes;

    }


        public void updateLayer() {
            clearLayer();


            if (listeSeismes == null || listeSeismes.isEmpty()) {
                return;
            }

            for (SisFranceModel seisme : listeSeismes) {
                try {
                    double latitude = Double.parseDouble(seisme.getLatitudeWGS84());
                    double longitude = Double.parseDouble(seisme.getLongitudeWGS84());

                    Point2D point2D = this.getMapPoint(latitude, longitude);
                    Circle circle = new Circle(5, Color.RED);
                    circle.setCenterX(point2D.getX());
                    circle.setCenterY(point2D.getY());
                    this.getChildren().add(circle);
                } catch (NumberFormatException e) {
                    // Gérer l'exception si la conversion en double échoue
                }
            }
        }



    protected void layoutLayer() {
        updateLayer();
    }


    public void clearLayer(){
            this.getChildren().clear();
        }
}
