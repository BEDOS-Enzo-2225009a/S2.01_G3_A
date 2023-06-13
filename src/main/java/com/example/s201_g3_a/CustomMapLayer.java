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


/**

 CustomMapLayer est une classe héritant de MapLayer, une classe fournie par la bibliothèque Gluon Maps.
 Elle représente une couche de carte personnalisée affichant des marqueurs pour les séismes.
 */
public class CustomMapLayer extends MapLayer {

    private List<SisFranceModel> listeSeismes;


    /**
     * Constructeur de la classe CustomMapLayer.
     *
     * @param listeSeismes La liste des séismes à afficher sur la couche de carte.
     */
    public CustomMapLayer(List<SisFranceModel> listeSeismes){
        this.listeSeismes=listeSeismes;

    }

    /**
     * Met à jour la couche de carte en affichant les marqueurs pour les séismes.
     */
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

    /**
     * Définit la liste des séismes à afficher sur la couche de carte.
     *
     * @param listeSeismes La liste des séismes.
     */
    public void setListeSeismes(List<SisFranceModel> listeSeismes) {
        this.listeSeismes = listeSeismes;
    }

    /**
     * Réalise le positionnement des éléments de la couche de carte.
     */
    protected void layoutLayer() {
        updateLayer();
    }


    /**
     * Efface tous les éléments de la couche de carte.
     */
    public void clearLayer(){
        this.getChildren().clear();
    }
}
