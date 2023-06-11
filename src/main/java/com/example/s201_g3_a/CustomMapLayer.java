package com.example.s201_g3_a;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.example.s201_g3_a.SisFranceView;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class CustomMapLayer extends MapLayer {


        public void updateLayer(ArrayList<DonneesSismiques> listeSeismes){
            clearLayer();
            for(DonneesSismiques seisme : listeSeismes){
                MapPoint point = new MapPoint(seisme.getLatitude(), seisme.getLongitude());
                Point2D point2D = getMapPoint(point.getLatitude(),point.getLongitude());
                Circle circle = new Circle(5, Color.RED);
                circle.setCenterX(point2D.getX());
                circle.setCenterY(point2D.getY());
                this.getChildren().add(circle);
                //oe oe
            }
        }

        public void clearLayer(){
            this.getChildren().clear();
        }
}
