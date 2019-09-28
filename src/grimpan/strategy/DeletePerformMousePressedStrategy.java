package grimpan.strategy;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import javafx.scene.input.MouseEvent;

public class DeletePerformMousePressedStrategy implements PerformMousePressedStrategy {
    ShapeFactory sf = null;
    GrimPanModel model = null;

    public DeletePerformMousePressedStrategy(GrimPanModel model, ShapeFactory sf){
        this.model = model;
        this.sf = sf;
    }

    @Override
    public void performMousePressed(MouseEvent event) {

    }
}
