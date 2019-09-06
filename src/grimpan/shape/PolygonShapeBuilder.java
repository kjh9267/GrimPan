package grimpan.shape;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

import grimpan.core.GrimPanModel;
import grimpan.core.Util;

public class PolygonShapeBuilder implements ShapeBuilder {

	GrimPanModel model = null;
	
	public PolygonShapeBuilder(GrimPanModel model){
		this.model = model;
	}

	@Override
	public void performMousePressed(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);

		model.polygonPoints.add(p1);
		model.curDrawShape = Util.buildPath2DFromPoints(model.polygonPoints);
		if (e.isShiftDown()) {
			((Path2D)(model.curDrawShape)).closePath();
			model.polygonPoints.clear();
			model.getController().addShapeAction();
		}
	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);

	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);

	}

}
