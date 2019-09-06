package grimpan.shape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

public class GrimShape implements Serializable, IGrimShape {

	private static final long serialVersionUID = 1L;
	private Shape shape = null;
	private float strokeWidth = 1f;
	private Color strokeColor = Color.black;
	private Paint grimPaint = null;

	public GrimShape(Shape shape){
		this(shape, 1f, Color.black, null);
	}
	/**
	 * @param shape
	 * @param grimStrokeWidth
	 * @param grimStrokeColor
	 * @param grimFill
	 * @param grimFillColor
	 */
	public GrimShape(Shape shape, float strokeWidth, Color strokeColor, Paint grimPaint) {
		this.shape = shape;
		this.strokeWidth = strokeWidth;
		this.strokeColor = strokeColor;
		this.grimPaint = grimPaint;
	}
	public GrimShape(IGrimShape grimShape){
		this.shape = grimShape.getShape();
		this.strokeWidth = grimShape.getStrokeWidth();
		this.strokeColor = grimShape.getStrokeColor();
		this.grimPaint = grimShape.getGrimPaint();
	}
	@Override
	public IGrimShape clone() {
		return new GrimShape(shape, strokeWidth, strokeColor, grimPaint);
	}

	@Override
	public void draw(Graphics2D g2){
		if (grimPaint!=null){
			g2.setPaint(grimPaint);
			g2.fill(shape);
		}
		
		if (strokeColor!=null){
			g2.setStroke(new BasicStroke(this.strokeWidth));
			g2.setColor(strokeColor);
			g2.draw(shape);
		}
		
	}

	@Override
	public void translate(float dx, float dy){
		AffineTransform tr = new AffineTransform();
		tr.setToTranslation(dx, dy);
		this.shape = tr.createTransformedShape(this.shape);
	}

	@Override
	public boolean contains(double px, double py){
		return this.shape.contains(px, py);
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public void setShape(Shape gpShape) {
		this.shape = gpShape;
	}

	@Override
	public float getStrokeWidth() {
		return strokeWidth;
	}

	@Override
	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	@Override
	public Color getStrokeColor() {
		return strokeColor;
	}

	@Override
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	@Override
	public Paint getGrimPaint() {
		return grimPaint;
	}

	@Override
	public void setGrimPaint(Paint grimPaint) {
		this.grimPaint = grimPaint;
	}

}