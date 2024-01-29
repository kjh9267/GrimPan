package grimpan.core;

import grimpan.svg.SVGGrimShape;
import javafx.scene.layout.Pane;

public class DrawPane extends Pane {

	private GrimPanModel model;

	public DrawPane(GrimPanModel model) {
		this(model, 600, 800);
	}
	public DrawPane(GrimPanModel model, double width, double height) {
		this.model = model;
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public void clear() {
		this.getChildren().clear();
	}
	public void redraw() {
		clear();
		//System.out.println("Shape Count="+model.shapeList.size());
		for (SVGGrimShape gsh:model.getShapeList()){
			this.getChildren().add(gsh.getShape());
		}
		if (model.getCurDrawShape()!=null && model.getCurDrawShape().getShape()!=null) {
			this.getChildren().add(model.getCurDrawShape().getShape());
			//System.out.println(model.shapeList);
		}
	}
}
