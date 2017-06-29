package sokobanSolver;

import java.util.List;

import common.data.Level2D;
import stripsLib.PlannableAction;
import stripsLib.Strips;

public class SokobanSolver {

	public SokoSolution solveLevel(Level2D level) {
		PlannableSokobanAdapter ps = new PlannableSokobanAdapter(level);
		Strips strips = new Strips();
		List<PlannableAction> list = strips.plan(ps);

		return new SokoSolution(list);
	}
}
