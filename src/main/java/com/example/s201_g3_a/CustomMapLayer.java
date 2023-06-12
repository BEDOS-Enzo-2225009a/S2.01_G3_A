package com.example.s201_g3_a;

import com.example.s201_g3_a.SisFranceModel;
import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class CustomMapLayer extends MapLayer {

    public void updateLayer(ArrayList<SisFranceModel> listeSeismes) {
        clearLayer();

        if (listeSeismes == null || listeSeismes.isEmpty()) {
            return;
        }

        for (SisFranceModel seisme : listeSeismes) {
            try {
                double latitude = Double.parseDouble(seisme.getLatitudeWGS84());
                double longitude = Double.parseDouble(seisme.getLongitudeWGS84());

                MapPoint point = new MapPoint(latitude, longitude);

                Point2D point2D = getMapPoint(point.getLatitude(), point.getLongitude());
                Circle circle = createCircle(point2D.getX(), point2D.getY(), 5, Paint.valueOf("RED"));
                this.getChildren().add(circle);
            } catch (NumberFormatException e) {
                // Gérer l'exception si la conversion en double échoue
            }
        }
    }

    public void clearLayer() {
        this.getChildren().clear();
    }

    private Circle createCircle(double centerX, double centerY, double radius, Paint fill) {
        Circle circle = new Circle(radius, fill);
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        return circle;
    }
}
