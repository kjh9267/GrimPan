package grimpan.state;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.DeletePerformMouseDraggedStrategy;
import grimpan.strategy.DeletePerformMousePressedStrategy;
import grimpan.strategy.DeletePerformMouseReleasedStrategy;

public class DeleteBuilderState extends EditState {

	ShapeFactory sf = null;	
	GrimPanModel model = null;
	
	public DeleteBuilderState(GrimPanModel model, ShapeFactory sf){
		performMouseDraggedStrategy = new DeletePerformMouseDraggedStrategy(model, sf);
		performMousePressedStrategy = new DeletePerformMousePressedStrategy(model, sf);
		performMouseReleasedStrategy = new DeletePerformMouseReleasedStrategy(model, sf);
	}
	@Override
	public int getStateType() {
		return EditState.EDIT_DELETE;
	}
}
