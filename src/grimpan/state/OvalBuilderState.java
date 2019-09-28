package grimpan.state;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.OvalPerformMouseDraggedStrategy;
import grimpan.strategy.OvalPerformMousePressedStrategy;
import grimpan.strategy.OvalPerformMouseReleasedStrategy;

public class OvalBuilderState extends EditState {

	ShapeFactory sf = null;	
	GrimPanModel model = null;
	
	public OvalBuilderState(GrimPanModel model, ShapeFactory sf){
		performMouseDraggedStrategy = new OvalPerformMouseDraggedStrategy(model, sf);
		performMousePressedStrategy = new OvalPerformMousePressedStrategy(model, sf);
		performMouseReleasedStrategy = new OvalPerformMouseReleasedStrategy(model, sf);
	}
	@Override
	public int getStateType() {
		return EditState.SHAPE_OVAL;
	}

}
