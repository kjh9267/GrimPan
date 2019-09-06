package grimpan.shape;

import java.awt.*;

public class GlassGrimShape extends DecorateGrimShape {

	Paint paint = null;
	public GlassGrimShape(IGrimShape grimShape) {
		super(grimShape);
		paint = TexturePaintFactory.createGlassPaint(grimShape.getShape());
	}
	@Override
	public IGrimShape clone() {
		return new GlassGrimShape(grimShape.clone());
	}
	@Override
	public void draw(Graphics2D g2) {
		g2.setPaint(paint);
		g2.fill(this.getShape());
	}

	
}
