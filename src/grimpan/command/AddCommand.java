package grimpan.command;

import grimpan.core.GrimPanModel;
import grimpan.svg.SVGGrimShape;

public class AddCommand implements Command {

	GrimPanModel model = null;
	SVGGrimShape grimShape = null;
	public AddCommand(GrimPanModel model, SVGGrimShape grimShape){
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
