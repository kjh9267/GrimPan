package grimpan.state;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.RegularPerformMouseDraggedStrategy;
import grimpan.strategy.RegularPerformMousePressedStrategy;
import grimpan.strategy.RegularPerformMouseReleasedStrategy;

public class RegularBuilderState extends EditState {

	ShapeFactory sf = null;	
	GrimPanModel model = null;
	
	public RegularBuilderState(GrimPanModel model, ShapeFactory sf){
		performMouseDraggedStrategy = new RegularPerformMouseDraggedStrategy(model, sf);
		performMousePressedStrategy = new RegularPerformMousePressedStrategy(model, sf);
		performMouseReleasedStrategy = new RegularPerformMouseReleasedStrategy(model, sf);
	}
	@Override
	public int getStateType() {
		return EditState.SHAPE_REGULAR;
	}
}
