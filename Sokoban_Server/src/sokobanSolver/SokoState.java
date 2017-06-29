package sokobanSolver;

import java.util.ArrayList;
import java.util.List;

import common.data.Position2D;

public class SokoState {

	private Position2D playerPos;
	private ArrayList<Position2D> boxesPos;

	public SokoState() {
		this.boxesPos = new ArrayList<>();
		this.playerPos = new Position2D();
	}

	public SokoState(Position2D playerPos, ArrayList<Position2D> boxesPos) {
		this.playerPos = playerPos;
		this.boxesPos = boxesPos;
	}

	public Position2D getPlayerPos() {
		return playerPos;
	}

	public List<Position2D> getBoxPos() {
		return boxesPos;
	}

	public boolean compareBoxesPos(List<Position2D> list1, List<Position2D> list2) {
		int counter = 0;
		if (list1.size() != list2.size())
			return false;

		for (Position2D p1 : list1) {
			for (Position2D p2 : list2) {
				if (p1.equals(p2))
					counter++;
			}
		}
		if (counter == list1.size())
			return true;
		else
			return false;

	}

	@Override
	public String toString() {
		return "player: (" + this.getPlayerPos() + ") box: (" + this.getBoxPos() + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (compareBoxesPos(this.boxesPos, ((SokoState) obj).boxesPos)) {
			return true;
		} else
			return false;
	}

}
