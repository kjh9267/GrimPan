package grimpan.state;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.LinePerformMouseDraggedStrategy;
import grimpan.strategy.LinePerformMousePressedStrategy;
import grimpan.strategy.LinePerformMouseReleasedStrategy;

public class LineBuilderState extends EditState {

	ShapeFactory sf = null;
	GrimPanModel model = null;

	public LineBuilderState(GrimPanModel model, ShapeFactory sf){
		performMouseDraggedStrategy = new LinePerformMouseDraggedStrategy(model, sf);
		performMousePressedStrategy = new LinePerformMousePressedStrategy(model, sf);
		performMouseReleasedStrategy = new LinePerformMouseReleasedStrategy(model, sf);
	}

	@Override
	public int getStateType() {
		return EditState.SHAPE_LINE;
	}

}
