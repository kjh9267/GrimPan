package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.interfaces.PerformMouseDraggedStrategy;
import javafx.scene.input.MouseEvent;

public class DeletePerformMouseDraggedStrategy implements PerformMouseDraggedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public DeletePerformMouseDraggedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMouseDragged(MouseEvent event) {

    }
}
