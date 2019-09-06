package grimpan.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import grimpan.shape.GrimShape;
import grimpan.shape.IGrimShape;

public class DrawPanelView 	extends JPanel{

	private static final long serialVersionUID = 1L;
	private GrimPanModel model = null;
	
	public DrawPanelView(){
		this.model = GrimPanModel.getInstance();
		DrawPanelMouseAdapter mouseAdapter = new DrawPanelMouseAdapter(model, this);
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (IGrimShape grimShape:model.shapeList){
			grimShape.draw(g2);
		}

		if (model.curDrawShape != null){
			IGrimShape curGrimShape = new GrimShape(model.curDrawShape, 
					model.getShapeStrokeWidth(), 
					model.getShapeStrokeColor(),
					model.getShapeFillColor());
			curGrimShape.draw(g2);
		}

	}

}
