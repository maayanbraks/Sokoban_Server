package sokobanSolver;

import java.util.LinkedList;

import common.data.Item;
import common.data.Level2D;
import common.data.Position2D;
import searchLib.Action;
import searchLib.Searchable;
import searchLib.State;




public abstract class CommonSearchable implements Searchable<Position2D> {
	private Position2D fromPos;
	private Position2D toPos;
	private Level2D level;
	protected char[][] map;
	protected LinkedList<Position2D> boxesPos;

	public CommonSearchable(Position2D fromPos, Position2D toPos, Level2D level) {
		super();

		this.boxesPos = new LinkedList<>();
		for (Item i : level.getBoxes())
			this.boxesPos.add(i.getPos());

		this.fromPos = fromPos;
		this.toPos = toPos;

		this.level = level;
		initMap();
	}

	public void initMap() {

		if (this.level != null) {
			this.map = level.toChar();
		}
	}

	@Override
	public State<Position2D> getInitialState() {
		State<Position2D> start = new State<>(null, 0, fromPos, null);
		return start;
	}

	@Override
	public State<Position2D> getGoalState() {
		State<Position2D> goal = new State<>(null, 0, toPos, null);
		return goal;
	}



	public LinkedList<Position2D> getBoxesPos() {
		return boxesPos;
	}

	public void setBoxesPos(LinkedList<Position2D> boxesPos) {
		this.boxesPos = boxesPos;
	}

	public Position2D getFromPos() {
		return fromPos;
	}

	public void setFromPos(Position2D fromPos) {
		this.fromPos = fromPos;
	}

	public Position2D getToPos() {
		return toPos;
	}

	public void setToPos(Position2D toPos) {
		this.toPos = toPos;
	}

	public Level2D getLevel() {
		return level;
	}

	public void setLevel(Level2D level) {
		this.level = level;
	}

	public abstract boolean isPossibleMove(Position2D currentPosition, Action action);

}
