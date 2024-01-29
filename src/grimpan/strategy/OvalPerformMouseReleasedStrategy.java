package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.interfaces.PerformMouseReleasedStrategy;
import grimpan.svg.SVGGrimEllipse;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

public class OvalPerformMouseReleasedStrategy implements PerformMouseReleasedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public OvalPerformMouseReleasedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMouseReleased(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setPrevMousePosition(model.getCurrMousePosition());
        model.setCurrMousePosition(p1);

        model.setCurDrawShape(new SVGGrimEllipse((Ellipse)(sf.createMousePointedEllipse())));
        if (model.getCurDrawShape() != null){
            model.getShapeList().add(model.getCurDrawShape());
            model.setCurDrawShape(null);
            model.getControl().addShapeAction();
        }
    }
}
