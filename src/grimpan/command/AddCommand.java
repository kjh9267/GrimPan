package grimpan.command;

import grimpan.core.GrimPanModel;
import grimpan.shape.GrimShape;

public class AddCommand implements Command {

	GrimPanModel model = null;
	GrimShape grimShape = null;
	public AddCommand(GrimPanModel model, GrimShape grimShape){
		this.model = model;
		this.grimShape = grimShape;
	}

	@Override
	public void execute() {
		if (model.curDrawShape != null){
			model.shapeList.add(grimShape);
			model.curDrawShape = null;
		}
	}

	@Override
	public void undo() {
		model.shapeList.remove(model.shapeList.size()-1);
	}

}
