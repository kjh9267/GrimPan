package grimpan.shape;

import grimpan.core.GrimPanModel;
import grimpan.core.Util;

import java.awt.*;
import java.awt.event.MouseEvent;

public class DecorateShapeBuilder implements ShapeBuilder {

	GrimPanModel model = null;
	
	public DecorateShapeBuilder(GrimPanModel model){
		this.model = model;
	}
	@Override
	public void performMousePressed(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);

		int selIndex = -1;
		IGrimShape shape = null;
		for (int i=model.shapeList.size()-1; i >= 0; --i){
			shape = model.shapeList.get(i);
			if (shape.contains(model.getMousePosition().getX(), model.getMousePosition().getY())){
				selIndex = i;
				break;
			}
		}
		model.setSelectedShapeIndex(selIndex);

		if (selIndex != -1){
			model.setLastMousePosition(model.getClickedMousePosition());
			Color scolor = shape.getStrokeColor();
			if (scolor!=null) {
				shape.setStrokeColor(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue(), 127));
			}
			if (shape.getGrimPaint() instanceof Color) {
				Color fcolor = (Color)shape.getGrimPaint();
				shape.setGrimPaint(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue(), 127));
			}
		}
	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		int selIndex = model.getSelectedShapeIndex();
		IGrimShape shape = null;
		if (selIndex != -1){
			shape = model.shapeList.get(selIndex);
			Color scolor = shape.getStrokeColor();
			if (scolor!=null) {
				shape.setStrokeColor(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue(), 255));
			}
			if (shape.getGrimPaint() instanceof Color) {
				Color fcolor = (Color)shape.getGrimPaint();
				shape.setGrimPaint(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue(), 255));
			}

			if (model.getEditState() == Util.DECO_GLASS) {
				model.shapeList.set(selIndex, new GlassGrimShape(shape));
			}
			else if (model.getEditState() == Util.DECO_TEX) {
				model.shapeList.set(selIndex, new TextureGrimShape(shape));
			}
			else if (model.getEditState() == Util.DECO_BALL) {
				model.shapeList.set(selIndex, new BallGrimShape(shape));
			}
			else if (model.getEditState() == Util.DECO_TRANS) {
				
			}
			model.getController().decorateShapeAction();
		}
	}

	@Override
	public void performMouseDragged(MouseEvent e) {

	}

}
