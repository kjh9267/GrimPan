package grimpan.command;

import grimpan.core.GrimPanModel;
import grimpan.shape.DecorateGrimShape;
import grimpan.shape.IGrimShape;

public class DecorateCommand implements Command {

	GrimPanModel model = null;
	IGrimShape savedGrimShape = null;
	int selIndex;
	public DecorateCommand(GrimPanModel model){
		this.model = model;
	}

	@Override
	public void execute() {
		savedGrimShape = ((DecorateGrimShape)model.shapeList.get(model.getSelectedShapeIndex()))
				.getGrimShape().clone();
		selIndex = model.getSelectedShapeIndex();
	}

	@Override
	public void undo() {
		if (selIndex != -1){
			model.shapeList.set(selIndex, savedGrimShape);
		}
		else {
			System.out.println("undo decoGrimShape not found!!");
		}
	}
}
