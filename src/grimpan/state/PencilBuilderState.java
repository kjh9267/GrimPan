package grimpan.state;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.PencilPerformMouseDraggedStrategy;
import grimpan.strategy.PencilPerformMousePressedStrategy;
import grimpan.strategy.PencilPerformMouseReleasedStrategy;

public class PencilBuilderState extends EditState {

	ShapeFactory sf = null;	
	GrimPanModel model = null;
	
	public PencilBuilderState(GrimPanModel model, ShapeFactory sf){
		performMouseDraggedStrategy = new PencilPerformMouseDraggedStrategy(model, sf);
		performMousePressedStrategy = new PencilPerformMousePressedStrategy(model, sf);
		performMouseReleasedStrategy = new PencilPerformMouseReleasedStrategy(model, sf);
	}
	@Override
	public int getStateType() {
		return EditState.SHAPE_PENCIL;
	}
}
