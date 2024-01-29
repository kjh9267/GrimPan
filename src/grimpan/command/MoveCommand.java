package grimpan.command;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.svg.SVGGrimShape;
import javafx.geometry.Point2D;

public class MoveCommand implements Command {

	GrimPanModel model = null;
	Point2D movedPos = null;
	SVGGrimShape movedShape = null;
	public MoveCommand(GrimPanModel model, Point2D moved){
		this.model = model;
		this.movedPos = moved;
	}

	@Override
	public void execute() {
		movedShape = model.getShapeList().get(model.getSelectedShapeIndex());
	}

	@Override
	public void undo() {
		int selIndex = model.getShapeList().indexOf(movedShape);
		if (selIndex != -1){
			ShapeFactory.translateShape(movedShape.getShape(), -movedPos.getX(), -movedPos.getY());
			//model.shapeList.set(selIndex, movedShape);
		}
		else {
			System.out.println("undo moved GrimShape not found!!");
		}
	}

}
