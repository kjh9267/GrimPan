package grimpan.state;

import grimpan.core.GrimPanModel;
import grimpan.core.ShapeFactory;
import grimpan.strategy.PolygonPerformMouseDraggedStrategy;
import grimpan.strategy.PolygonPerformMousePressedStrategy;
import grimpan.strategy.PolygonPerformMouseReleasedStrategy;

public class PolygonBuilderState extends EditState {

	ShapeFactory sf = null;	
	GrimPanModel model = null;
	
	public PolygonBuilderState(GrimPanModel model, ShapeFactory sf){
		performMouseDraggedStrategy = new PolygonPerformMouseDraggedStrategy(model, sf);
		performMousePressedStrategy = new PolygonPerformMousePressedStrategy(model, sf);
		performMouseReleasedStrategy = new PolygonPerformMouseReleasedStrategy(model, sf);
	}
	@Override
	public int getStateType() {
		return EditState.SHAPE_POLYGON;
	}
}
