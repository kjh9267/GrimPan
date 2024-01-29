package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.interfaces.PerformMouseReleasedStrategy;
import javafx.scene.input.MouseEvent;

public class DeletePerformMouseReleasedStrategy implements PerformMouseReleasedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public DeletePerformMouseReleasedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMouseReleased(MouseEvent event) {

    }
}
