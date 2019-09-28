package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.svg.SVGGrimLine;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

import javafx.scene.input.MouseEvent;


public class LinePerformMouseReleasedStrategy implements PerformMouseReleasedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public LinePerformMouseReleasedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMouseReleased(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setPrevMousePosition(model.getCurrMousePosition());
        model.setCurrMousePosition(p1);

        model.curDrawShape = new SVGGrimLine((Line)(sf.createMousePointedLine()));
        if (model.curDrawShape != null) {
            model.shapeList.add(model.curDrawShape);
            model.curDrawShape = null;
            model.control.addShapeAction();
        }
    }
}
