package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.interfaces.PerformMouseDraggedStrategy;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;

public class PencilPerformMouseDraggedStrategy implements PerformMouseDraggedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public PencilPerformMouseDraggedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMouseDragged(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setPrevMousePosition(model.getCurrMousePosition());
        model.setCurrMousePosition(p1);

        ((Path)model.getCurDrawShape().getShape()).getElements().add(new LineTo(p1.getX(), p1.getY()));
    }
}
