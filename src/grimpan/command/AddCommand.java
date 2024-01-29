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
		if (model.getCurDrawShape() != null){
			model.getShapeList().add(grimShape);
			model.setCurDrawShape(null);
		}
	}

	@Override
	public void undo() {
		model.getShapeList().remove(model.getShapeList().size()-1);
	}

}
