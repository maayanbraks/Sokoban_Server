package sokobanSolver;

import java.util.LinkedList;
import java.util.List;

import searchLib.Action;
import stripsLib.PlannableAction;



public class SokoSolution {

	private List<Action> solution;

	public SokoSolution(List<PlannableAction> solution) {

		this.solution = new LinkedList<>();
		for (PlannableAction pa : solution) {
			for (Action action : pa.getSubActions()) {

				this.solution.add(action);
			}
		}
	}

	@Override
	public String toString() {
		int actionNum = 0;
		String str = "********************\n" + "******Solution******\n" + "********************\n";
		for (Action a : solution) {
			actionNum++;
			str += actionNum + ") " + a.toString().toUpperCase() + "\n";
		}
		str += "********************";
		return str;
	}
}
