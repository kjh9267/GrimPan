package grimpan.shape;

import grimpan.core.GrimPanModel;
import grimpan.core.Util;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class OvalShapeBuilder implements ShapeBuilder {

	GrimPanModel model = null;
	
	public OvalShapeBuilder(GrimPanModel model){
		this.model = model;
	}

	@Override
	public void performMousePressed(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);

		genEllipse2D();
	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);

		genEllipse2D();
		model.getController().addShapeAction();
	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);

		genEllipse2D();
	}
	
	private void genEllipse2D(){
		Point pi = model.getMousePosition();
		Point topleft = model.getClickedMousePosition();
		if (pi.distance(new Point2D.Double(topleft.x, topleft.y)) <= Util.MINPOLYDIST)
			return;
		Ellipse2D oval = new Ellipse2D.Double(
				topleft.x, topleft.y,
				pi.x-topleft.x, pi.y-topleft.y);
		model.curDrawShape = oval;
	}

}
