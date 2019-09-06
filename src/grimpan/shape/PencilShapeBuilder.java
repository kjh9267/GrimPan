package grimpan.shape;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

import grimpan.core.GrimPanModel;

public class PencilShapeBuilder implements ShapeBuilder {

	GrimPanModel model = null;
	
	public PencilShapeBuilder(GrimPanModel model){
		this.model = model;
	}

	@Override
	public void performMousePressed(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);
		model.setLastMousePosition(model.getMousePosition());				
		model.curDrawShape = new Path2D.Double();
		((Path2D)model.curDrawShape).moveTo((double)p1.x, (double)p1.y);

	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		((Path2D)model.curDrawShape).lineTo((double)p1.x, (double)p1.y);
		model.getController().addShapeAction();

	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);
		((Path2D)model.curDrawShape).lineTo((double)p1.x, (double)p1.y);

	}

}
