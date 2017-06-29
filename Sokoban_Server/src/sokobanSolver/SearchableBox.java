package sokobanSolver;

import java.util.HashMap;

import common.data.Level2D;
import common.data.Position2D;
import searchLib.Action;
import searchLib.ComplicatedAction;
import searchLib.Searcher;
import searchLib.Solution;
import searchLib.State;


public class SearchableBox extends CommonSearchable {

	private Searcher<Position2D> searcher;
	private SearchablePlayer player;
	private Position2D playerPos;
	private Position2D searchableBoxPos;

	public SearchableBox(Position2D fromPos, Position2D toPos, Level2D level, Searcher<Position2D> searcher,
			SearchablePlayer player) {
		super(fromPos, toPos, level);
		this.searcher = searcher;
		this.player = player;
		if (player != null)
			this.playerPos = player.getFromPos();
	}

	public Position2D wherePlayerShouldBe(Position2D boxPos, Action action) {
		switch (action) {
		case UP:
			return boxPos.down();
		case DOWN:
			return boxPos.up();
		case RIGHT:
			return boxPos.left();
		case LEFT:
			return boxPos.right();
		}
		return null;
	}

	public Position2D getBoxNextPos(Position2D pos, Action action) {

		switch (action) {
		case UP:
			return pos.up();
		case DOWN:
			return pos.down();
		case RIGHT:
			return pos.right();
		case LEFT:
			return pos.left();
		}
		return null;

	}

	@Override
	public HashMap<ComplicatedAction, State<Position2D>> getAllPossibleStates(State<Position2D> state) {

		if (state.getCameFrom() != null) {
			Position2D tempPos = null;
			tempPos = state.getCameFrom().getState();
			this.playerPos = tempPos;
			this.boxesPos.remove(this.searchableBoxPos);
			this.boxesPos.add(state.getState());

			this.player.setBoxesPos(this.boxesPos);
		}

		Solution path = null;
		this.player.setFromPos(this.playerPos);
		this.player.setLevel(getLevel());
		State<Position2D> tempState = null;
		Position2D boxNextPos = null;
		ComplicatedAction compAction = null;
		HashMap<ComplicatedAction, State<Position2D>> possibleStates = new HashMap<>();
		Position2D boxPos = state.getState();
		for (Action action : Action.values()) {
			if (this.isPossibleMove(boxPos, action)) {
				this.player.setFromPos(this.playerPos);
				this.player.setToPos(this.wherePlayerShouldBe(boxPos, action));
				this.player.setLevel(getLevel());
				path = searcher.search(player);
				if (path != null) {
					boxNextPos = this.getBoxNextPos(boxPos, action);
					compAction = new ComplicatedAction(action, path.getActions());
					tempState = new State<Position2D>(state, state.getCost() + 1, boxNextPos, compAction);
					possibleStates.put(compAction, tempState);

				}
			}
		}
		if (state.getCameFrom() != null) {

			this.boxesPos.remove(state.getState());
			this.boxesPos.add(this.searchableBoxPos);

		}
		return possibleStates;

	}

	public Position2D getSearchableBoxPos() {
		return this.searchableBoxPos;
	}

	public void setSearchableBoxPos(Position2D searchableBoxPos) {
		this.searchableBoxPos = searchableBoxPos;
	}



	public Searcher<Position2D> getSearcher() {
		return this.searcher;
	}

	public void setSearcher(Searcher<Position2D> searcher) {
		this.searcher = searcher;
	}

	public SearchablePlayer getPlayer() {
		return this.player;
	}

	public Position2D getPlayerPos() {
		return this.playerPos;
	}

	public void setPlayerPos(Position2D playerPos) {
		if (this.player != null)
			this.player.setFromPos(playerPos);
		this.playerPos = playerPos;
	}

	@Override
	public boolean isPossibleMove(Position2D destination, Action action) {
		Position2D playerPos = null;
		Position2D goalPos = null;
		switch (action) {
		case UP:
			playerPos = destination.down();
			goalPos = destination.up();
			break;
		case DOWN:
			playerPos = destination.up();
			goalPos = destination.down();

			break;
		case RIGHT:
			playerPos = destination.left();
			goalPos = destination.right();
			break;
		case LEFT:
			playerPos = destination.right();
			goalPos = destination.left();
			break;
		}
		if (this.getLevel().isOnMap(playerPos) && this.getLevel().isOnMap(goalPos))

		{
			char player, goal;
			player = this.map[playerPos.getX()][playerPos.getY()];
			goal = this.map[goalPos.getX()][goalPos.getY()];

			if ((player == 'A' || player == ' ' || player == 'o') && (goal == 'A' || goal == ' ' || goal == 'o'))

				return true;
			else if (player == '#' || goal == '#')
				return false;
			else {
				for (Position2D p : this.boxesPos) {
					if (playerPos.equals(p) || (goalPos.equals(p)))
						return false;
				}
				return true;
			}
		}
		return false;
	}



}
