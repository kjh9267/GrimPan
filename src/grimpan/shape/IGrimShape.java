package grimpan.shape;

import java.awt.*;

public interface IGrimShape {
	
	public IGrimShape clone();

	public void draw(Graphics2D g2);

	public void translate(float dx, float dy);

	public boolean contains(double px, double py);

	public Shape getShape();

	public void setShape(Shape gpShape);

	public float getStrokeWidth();

	public void setStrokeWidth(float strokeWidth);

	public Color getStrokeColor();

	public void setStrokeColor(Color strokeColor);

	public Paint getGrimPaint();

	public void setGrimPaint(Paint grimPaint);

}