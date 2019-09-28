package grimpan.state;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.MovePerformMouseDraggedStrategy;
import grimpan.strategy.MovePerformMousePressedStrategy;
import grimpan.strategy.MovePerformMouseReleasedStrategy;

public class MoveBuilderState extends EditState {

	ShapeFactory sf = null;	
	GrimPanModel model = null;

	public MoveBuilderState(GrimPanModel model, ShapeFactory sf){
		performMouseDraggedStrategy = new MovePerformMouseDraggedStrategy(model, sf);
		performMousePressedStrategy = new MovePerformMousePressedStrategy(model, sf);
		performMouseReleasedStrategy = new MovePerformMouseReleasedStrategy(model, sf);
	}
	@Override
	public int getStateType() {
		return EditState.EDIT_MOVE;
	}

}
