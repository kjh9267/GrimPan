package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.interfaces.PerformMouseReleasedStrategy;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;

public class PencilPerformMouseReleasedStrategy implements PerformMouseReleasedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public PencilPerformMouseReleasedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMouseReleased(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setPrevMousePosition(model.getCurrMousePosition());
        model.setCurrMousePosition(p1);

        ((Path)model.getCurDrawShape().getShape()).getElements().add(new LineTo(p1.getX(), p1.getY()));
        if (model.getCurDrawShape() != null){
            model.getShapeList().add(model.getCurDrawShape());
            model.setCurDrawShape(null);
            model.getControl().addShapeAction();
        }
    }
}
