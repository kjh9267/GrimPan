package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.svg.SVGGrimLine;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

import javafx.scene.input.MouseEvent;

public class LinePerformMousePressedStrategy implements PerformMousePressedStrategy {
    GrimPanModel model = null;
    ShapeFactory sf = null;

    public LinePerformMousePressedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMousePressed(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setStartMousePosition(p1);
        model.setCurrMousePosition(p1);
        model.setPrevMousePosition(p1);

        model.curDrawShape = new SVGGrimLine((Line)(sf.createMousePointedLine()));
    }
}
