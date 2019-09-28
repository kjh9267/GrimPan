package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.svg.SVGGrimPolygon;
import grimpan.svg.SVGGrimPolyline;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class PolygonPerformMousePressedStrategy implements PerformMousePressedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public PolygonPerformMousePressedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMousePressed(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setStartMousePosition(p1);
        model.setCurrMousePosition(p1);
        model.setPrevMousePosition(p1);

        model.polygonPoints.add(model.getCurrMousePosition());
        if (event.isShiftDown()) {
            //((Path)model.curDrawShape).getElements().add(new ClosePath());
            model.curDrawShape = new SVGGrimPolygon((Polygon)(sf.createPolygonFromClickedPoints()));
            model.polygonPoints.clear();
            model.shapeList.add(model.curDrawShape);
            model.curDrawShape = null;
            model.control.addShapeAction();
        }
        else {
            model.curDrawShape = new SVGGrimPolyline((Polyline)(sf.createPolylineFromClickedPoints()));
        }
    }
}
