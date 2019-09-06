package grimpan;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class LineShapeBuilder implements ShapeBuilder {

	GrimPanModel model = null;
	
	public LineShapeBuilder(GrimPanModel model){
		this.model = model;
	}

	@Override
	public void performMousePressed(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);

		genLineShape();
	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);

		genLineShape();
		if (model.curDrawShape != null){
			model.shapeList
			.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
					model.getShapeStrokeColor(), model.isShapeFill(), model.getShapeFillColor()));
			model.curDrawShape = null;
		}
	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);

		genLineShape();
	}
	private void genLineShape() {
		Point p1 = model.getClickedMousePosition();
		Point p2 = model.getMousePosition();
		model.curDrawShape = new Line2D.Double(p1, p2);
		
	}

}
