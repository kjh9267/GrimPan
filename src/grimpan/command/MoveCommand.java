package grimpan.command;

import java.awt.Paint;
import java.awt.Shape;

import grimpan.core.GrimPanModel;
import grimpan.shape.IGrimShape;

public class MoveCommand implements Command {

	GrimPanModel model = null;
	Shape savedShape = null;
	Paint savedPaint = null;
	IGrimShape movedShape = null;
	IGrimShape savedGrimShape = null;
	public MoveCommand(GrimPanModel model, IGrimShape grimShape){
		this.model = model;
		this.savedShape = grimShape.getShape();
		this.savedPaint = grimShape.getGrimPaint();
		this.savedGrimShape = grimShape;
	}

	@Override
	public void execute() {
		movedShape = model.shapeList.get(model.getSelectedShapeIndex());
	}

	@Override
	public void undo() {
		int selIndex = model.shapeList.indexOf(movedShape);
		if (selIndex != -1){
			//model.shapeList.get(selIndex).setShape(savedShape);
			//model.shapeList.get(selIndex).setGrimPaint(savedPaint);
			model.shapeList.set(selIndex, savedGrimShape);
		}
		else {
			System.out.println("undo moved GrimShape not found!!");
		}
	}

}
