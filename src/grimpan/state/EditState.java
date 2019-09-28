package grimpan.state;

import grimpan.strategy.*;
import javafx.scene.input.MouseEvent;

public abstract class EditState {
	PerformMousePressedStrategy performMousePressedStrategy;
	PerformMouseReleasedStrategy performMouseReleasedStrategy;
	PerformMouseDraggedStrategy performMouseDraggedStrategy;

	public static final int SHAPE_REGULAR = 0;
	public static final int SHAPE_OVAL = 1;
	public static final int SHAPE_POLYGON = 2;
	public static final int SHAPE_LINE = 3;
	public static final int SHAPE_PENCIL = 4;
	public static final int EDIT_MOVE = 5;
	public static final int EDIT_DELETE = 6;
	public static final int EDIT_LOADING = 7;

	
	public void performMousePressed(MouseEvent e){ performMousePressedStrategy.performMousePressed(e); };
	public void performMouseReleased(MouseEvent e){
		performMouseReleasedStrategy.performMouseReleased(e);
	};
	public void performMouseDragged(MouseEvent e) {
		performMouseDraggedStrategy.performMouseDragged(e);
	};
	public abstract int getStateType();

	// setter method
	public void setPerformMousePressedStrategy(PerformMousePressedStrategy performMousePressedStrategy){
		this.performMousePressedStrategy = performMousePressedStrategy;
	}
	public void setPerformMouseReleasedStrategy(PerformMouseReleasedStrategy performMouseReleasedStrategy){
		this.performMouseReleasedStrategy = performMouseReleasedStrategy;
	}
	public void setPerformMouseDraggedStrategy(PerformMouseDraggedStrategy performMouseDraggedStrategy){
		this.performMouseDraggedStrategy = performMouseDraggedStrategy;
	}
}
