package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.interfaces.PerformMouseDraggedStrategy;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public class MovePerformMouseDraggedStrategy implements PerformMouseDraggedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public MovePerformMouseDraggedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMouseDragged(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setPrevMousePosition(model.getCurrMousePosition());
        model.setCurrMousePosition(p1);

        if (model.getSelectedShapeIndex()!=-1) {
            moveShapeByMouse();
        }
    }

    private void moveShapeByMouse(){
        int selIndex = model.getSelectedShapeIndex();
        Shape shape = null;
        if (selIndex != -1){
            shape = model.getShapeList().get(selIndex).getShape();
            double dx = model.getCurrMousePosition().getX() - model.getPrevMousePosition().getX();
            double dy = model.getCurrMousePosition().getY() - model.getPrevMousePosition().getY();

            ShapeFactory.translateShape(shape, dx, dy);
        }
    }
}
