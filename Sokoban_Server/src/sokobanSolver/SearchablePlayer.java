package sokobanSolver;

import java.util.HashMap;

import common.data.Item;
import common.data.Level2D;
import common.data.Position2D;
import searchLib.Action;
import searchLib.ComplicatedAction;
import searchLib.State;


public class SearchablePlayer extends CommonSearchable {


	public SearchablePlayer(Position2D fromPos, Position2D toPos, Level2D level) {
		super(fromPos, toPos, level);
	}


	@Override
	public HashMap<ComplicatedAction, State<Position2D>> getAllPossibleStates(State<Position2D> state) {
		HashMap<ComplicatedAction, State<Position2D>> possibleStates = new HashMap<>();
		Position2D currentPosition2D = state.getState();

		// UP
		if (isPossibleMove(currentPosition2D, Action.UP))
			possibleStates.put(new ComplicatedAction(Action.UP, null), new State<>(state, state.getCost() + 1,
					currentPosition2D.up(), new ComplicatedAction(Action.UP, null)));
		// DOWN
		if (isPossibleMove(currentPosition2D, Action.DOWN))
			possibleStates.put(new ComplicatedAction(Action.DOWN, null), new State<>(state, state.getCost() + 1,
					currentPosition2D.down(), new ComplicatedAction(Action.DOWN, null)));
		// RIGHT
		if (isPossibleMove(currentPosition2D, Action.RIGHT))
			possibleStates.put(new ComplicatedAction(Action.RIGHT, null), new State<>(state, state.getCost() + 1,
					currentPosition2D.right(), new ComplicatedAction(Action.RIGHT, null)));
		// LEFT
		if (isPossibleMove(currentPosition2D, Action.LEFT))
			possibleStates.put(new ComplicatedAction(Action.LEFT, null), new State<>(state, state.getCost() + 1,
					currentPosition2D.left(), new ComplicatedAction(Action.LEFT, null)));

		return possibleStates;
	}

	@Override
	public boolean isPossibleMove(Position2D destination, Action action) {
		Position2D dest = null;

		Item i = null;

		switch (action) {
		case UP:
			dest = destination.up();
			break;
		case DOWN:
			dest = destination.down();
			break;
		case LEFT:
			dest = destination.left();
			break;
		case RIGHT:
			dest = destination.right();
			break;
		}

		if (dest != null) {
			if (this.getLevel().isOnMap(dest)) {
				for (Position2D p : boxesPos)
					if (dest.equals(p))
						return false;

				i = this.getLevel().getItemInPlace(dest);
				if (i.getCh() == '#')
					return false;
				return true;
			} else
				return false;
		}
		return false;
	}

}
