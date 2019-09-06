package grimpan.shape;

import java.awt.*;

public abstract class DecorateGrimShape implements IGrimShape {

	protected IGrimShape grimShape = null;
	
	public DecorateGrimShape(IGrimShape grimShape) {
		this.grimShape = grimShape;
	}
	public IGrimShape getGrimShape() {
		return grimShape;
	}

	@Override
	public abstract IGrimShape clone();
	
	@Override
	public void draw(Graphics2D g2) {
		this.grimShape.draw(g2);
	}
	@Override
	public void translate(float dx, float dy) {
		this.grimShape.translate(dx, dy);
	}
	@Override
	public boolean contains(double px, double py) {
		return this.grimShape.contains(px, py);
	}
	@Override
	public Shape getShape() {
		return this.grimShape.getShape();
	}
	@Override
	public void setShape(Shape shape) {
		this.grimShape.setShape(shape);
		
	}
	@Override
	public float getStrokeWidth() {
		return this.grimShape.getStrokeWidth();
	}
	@Override
	public void setStrokeWidth(float strokeWidth) {
		this.grimShape.setStrokeWidth(strokeWidth);
		
	}
	@Override
	public Color getStrokeColor() {
		return this.grimShape.getStrokeColor();
	}
	@Override
	public void setStrokeColor(Color strokeColor) {
		this.grimShape.setStrokeColor(strokeColor);
		
	}
	@Override
	public Paint getGrimPaint() {
		return this.grimShape.getGrimPaint();
	}
	@Override
	public void setGrimPaint(Paint grimPaint) {
		this.grimShape.setGrimPaint(grimPaint);	
	}
}
