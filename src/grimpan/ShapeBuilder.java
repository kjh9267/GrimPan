package grimpan;

import java.awt.event.MouseEvent;

public interface ShapeBuilder {

	void performMousePressed(MouseEvent e);
	void performMouseReleased(MouseEvent e);
	void performMouseDragged(MouseEvent e);
}
