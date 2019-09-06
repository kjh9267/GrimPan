package grimpan;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

public class GrimShape implements Serializable {

	private static final long serialVersionUID = 1L;
	private Shape grimShape = null;
	private float grimStrokeWidth = 1f;
	private Color grimStrokeColor = null;
	private boolean grimFill = false;
	private Color grimFillColor = null;

	public GrimShape(Shape grimShape){
		this(grimShape, 1f, Color.black, false, Color.black);
	}
	/**
	 * @param grimShape
	 * @param grimStrokeWidth
	 * @param grimStrokeColor
	 * @param grimFill
	 * @param grimFillColor
	 */
	public GrimShape(Shape grimShape, float grimStrokeWidth, Color grimStrokeColor,
			boolean grimFill, Color grimFillColor) {
		super();
		this.grimShape = grimShape;
		this.grimStrokeWidth = grimStrokeWidth;
		this.grimStrokeColor = grimStrokeColor;
		this.grimFill = grimFill;
		this.grimFillColor = grimFillColor;
	}
	
	public void draw(Graphics2D g2){
		if (isGrimFill()){
			g2.setColor(grimFillColor);
			g2.fill(grimShape);
		}
		
		if (grimStrokeColor!=null){
			g2.setStroke(new BasicStroke(this.grimStrokeWidth));
			g2.setColor(grimStrokeColor);
			g2.draw(grimShape);
		}
		
	}
	
	public void translate(float dx, float dy){
		AffineTransform tr = new AffineTransform();
		tr.setToTranslation(dx, dy);
		this.grimShape = tr.createTransformedShape(this.grimShape);
	}

	public boolean contains(double px, double py){
		return this.grimShape.contains(px, py);
	}

	/**
	 * @return the grimShape
	 */
	public Shape getGrimShape() {
		return grimShape;
	}
	/**
	 * @param grimShape the grimShape to set
	 */
	public void setGrimShape(Shape grimShape) {
		this.grimShape = grimShape;
	}
	/**
	 * @return the grimStrokeWidth
	 */
	public float getGrimStrokeWidth() {
		return grimStrokeWidth;
	}
	/**
	 * @param grimStrokeWidth the grimStrokeWidth to set
	 */
	public void setGrimStrokeWidth(float grimStrokeWidth) {
		this.grimStrokeWidth = grimStrokeWidth;
	}
	/**
	 * @return the grimStrokeColor
	 */
	public Color getGrimStrokeColor() {
		return grimStrokeColor;
	}
	/**
	 * @param grimStrokeColor the grimStrokeColor to set
	 */
	public void setGrimStrokeColor(Color grimStrokeColor) {
		this.grimStrokeColor = grimStrokeColor;
	}
	/**
	 * @return the grimFill
	 */
	public boolean isGrimFill() {
		return grimFill;
	}
	/**
	 * @param grimFill the grimFill to set
	 */
	public void setGrimFill(boolean grimFill) {
		this.grimFill = grimFill;
	}
	/**
	 * @return the grimFillColor
	 */
	public Color getGrimFillColor() {
		return grimFillColor;
	}
	/**
	 * @param grimFillColor the grimFillColor to set
	 */
	public void setGrimFillColor(Color grimFillColor) {
		this.grimFillColor = grimFillColor;
	}
}