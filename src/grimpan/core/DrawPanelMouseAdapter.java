package grimpan.core;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class DrawPanelMouseAdapter extends MouseInputAdapter {

	private GrimPanModel model = null;
	private DrawPanelView drawView = null;
	
	public DrawPanelMouseAdapter(GrimPanModel model, DrawPanelView drawView){
		this.model = model;
		this.drawView = drawView;
	}
	public void mousePressed(MouseEvent ev) {

		if (SwingUtilities.isLeftMouseButton(ev)){
			model.sb.performMousePressed(ev);
		}
		drawView.repaint();
	}

	public void mouseReleased(MouseEvent ev) {

		if (SwingUtilities.isLeftMouseButton(ev)){
			model.sb.performMouseReleased(ev);
		}
		drawView.repaint();

	}

	public void mouseDragged(MouseEvent ev) {
		
		if (SwingUtilities.isLeftMouseButton(ev)){
			model.sb.performMouseDragged(ev);
		}
		drawView.repaint();

	}

}
