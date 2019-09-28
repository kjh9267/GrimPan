package grimpan.state;

import grimpan.strategy.LoadingPerformMouseDraggedStrategy;
import grimpan.strategy.LoadingPerformMousePressedStrategy;
import grimpan.strategy.LoadingPerformMouseReleasedStrategy;

public class LoadingState extends EditState {

	public LoadingState(){
		performMouseDraggedStrategy = new LoadingPerformMouseDraggedStrategy();
		performMousePressedStrategy = new LoadingPerformMousePressedStrategy();
		performMouseReleasedStrategy = new LoadingPerformMouseReleasedStrategy();
	}

	@Override
	public int getStateType() {
		return EditState.EDIT_LOADING;
	}
}
