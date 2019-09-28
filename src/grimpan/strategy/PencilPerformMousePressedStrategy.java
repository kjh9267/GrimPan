package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.svg.SVGGrimPath;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class PencilPerformMousePressedStrategy implements PerformMousePressedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public PencilPerformMousePressedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMousePressed(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setStartMousePosition(p1);
        model.setCurrMousePosition(p1);
        model.setPrevMousePosition(p1);

        model.curDrawShape = new SVGGrimPath((Path)(sf.createPaintedShape(new Path(new MoveTo(p1.getX(), p1.getY())))));
    }
}
