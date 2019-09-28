package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class PolygonPerformMouseDraggedStrategy implements PerformMouseDraggedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public PolygonPerformMouseDraggedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMouseDragged(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setPrevMousePosition(model.getCurrMousePosition());
        model.setCurrMousePosition(p1);
    }
}
