package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.interfaces.PerformMouseReleasedStrategy;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class MovePerformMouseReleasedStrategy implements PerformMouseReleasedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public MovePerformMouseReleasedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMouseReleased(MouseEvent event) {
        Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
        model.setPrevMousePosition(model.getCurrMousePosition());
        model.setCurrMousePosition(p1);

        if (model.getSelectedShapeIndex() != -1) {
            endShapeMove();

            double dx = model.getCurrMousePosition().getX() - model.getStartMousePosition().getX();
            double dy = model.getCurrMousePosition().getY() - model.getStartMousePosition().getY();
            model.setMovedPos(new Point2D(dx, dy));

            model.getControl().moveShapeAction();
        }
    }
    private void endShapeMove(){
        int selIndex = model.getSelectedShapeIndex();
        Shape shape = null;
        if (selIndex != -1){
            shape = model.getShapeList().get(selIndex).getShape();
            Color scolor = (Color)shape.getStroke();
            Color fcolor = (Color)shape.getFill();
            if (shape.getStroke()!=Color.TRANSPARENT){
                shape.setStroke(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue(), 1));
            }
            if (shape.getFill()!=Color.TRANSPARENT){
                shape.setFill(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue(), 1));
            }
            double dx = model.getCurrMousePosition().getX() - model.getPrevMousePosition().getX();
            double dy = model.getCurrMousePosition().getY() - model.getPrevMousePosition().getY();

            ShapeFactory.translateShape(shape, dx, dy);
        }
    }


}
