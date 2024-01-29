package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.interfaces.PerformMousePressedStrategy;
import grimpan.svg.SVGGrimEllipse;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

public class OvalPerformMousePressedStrategy implements PerformMousePressedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public OvalPerformMousePressedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMousePressed(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setStartMousePosition(p1);
        model.setCurrMousePosition(p1);
        model.setPrevMousePosition(p1);

        model.setCurDrawShape(new SVGGrimEllipse((Ellipse)(sf.createMousePointedEllipse())));
    }
}
