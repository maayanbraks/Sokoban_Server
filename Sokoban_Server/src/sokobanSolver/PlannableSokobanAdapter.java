package sokobanSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import common.data.Level2D;
import common.data.Position2D;
import searchLib.BFS;
import searchLib.Solution;
import stripsLib.Clause;
import stripsLib.Plannable;
import stripsLib.PlannableAction;
import stripsLib.Predicate;


public class PlannableSokobanAdapter implements Plannable {

	private Level2D level;
	private Clause goal;
	private Clause kb;
	private BFS<Position2D> bfs;
	private SearchableBox searchable;
	private static Position2D playerPos;
	private static LinkedList<Position2D> boxesPos;

	public PlannableSokobanAdapter(Level2D level) {
		this.level = level;

		boxesPos = new LinkedList<>();
		bfs = new BFS<>();

		if (level != null) {
			kb = new Clause();
			goal = new Clause();
			Predicate predicte = null;
			int boxIdNumber = 1;
			for (int i = 0; i < level.getHeight(); i++)
				for (int j = 0; j < level.getWidth(); j++) {

					switch (level.getMap()[i][j].getCh()) {
					case 'A':
						playerPos = new Position2D(i, j);
						predicte = new SokoPredicate("PlayerAt", "Player1", "(" + i + "," + j + ")");
						kb.updated(predicte);
						break;
					case '@':
						boxesPos.add(new Position2D(i, j));
						predicte = new SokoPredicate("BoxAt", "box" + boxIdNumber, "(" + i + "," + j + ")");
						boxIdNumber++;
						kb.updated(predicte);
						break;
					case 'o':
						predicte = new SokoPredicate("ClearAt", "(" + i + "," + j + ")", "(" + i + "," + j + ")");
						kb.updated(predicte);
						break;
					case ' ':
						predicte = new SokoPredicate("ClearAt", "(" + i + "," + j + ")", "(" + i + "," + j + ")");
						kb.updated(predicte);
						break;
					default:
						break;
					}
				}
			HashMap<String, String> hm = this.matchBoxToTargert(level);
			for (String id : hm.keySet()) {
				predicte = new SokoPredicate("BoxAt", id, hm.get(id));
				goal.updated(predicte);
			}
			searchable = new SearchableBox(playerPos, playerPos, level, bfs,
					new SearchablePlayer(playerPos, playerPos, level));
			searchable.getPlayer().setBoxesPos(boxesPos);
		}
	}

	private HashMap<String, String> matchBoxToTargert(Level2D level) {
		HashMap<String, String> hm = new HashMap<>();
		ArrayList<Position2D> boxesTempPos = new ArrayList<>();
		ArrayList<Position2D> targetBoxesTempPos = new ArrayList<>();
		for (int i = 0; i < level.getBoxes().size(); i++) {
			boxesTempPos.add(level.getBoxes().get(i).getPos());
			targetBoxesTempPos.add(level.getTargetBoxes().get(i).getPos());
			hm.put("box" + (i + 1), targetBoxesTempPos.get(i).toString());
		}
		return hm;
	}

	@Override
	public Clause getGoal() {
		return this.goal;
	}

	@Override
	public Clause getKnowledgebase() {
		return this.kb;
	}

	@Override
	public Set<PlannableAction> getsatisfyingActions(Predicate top) {
		Set<PlannableAction> satisfying = null;
		Predicate boxKbPred = null;
		Position2D boxTempPos = null;
		Predicate playerKbPred = null;
		Position2D playerTempPos = null;
		Position2D goalPos = null;
		if (top.getType().equals("BoxAt")) {

			for (Predicate p : kb.getPredicates()) {
				if (top.getId().equals(p.getId()))
					boxKbPred = p;
				if (p.getType().equals("PlayerAt"))
					playerKbPred = p;
			}
			playerTempPos = new Position2D(playerKbPred.getValue().toCharArray()[1] - '0',
					playerKbPred.getValue().toCharArray()[3] - '0');
			boxTempPos = new Position2D(boxKbPred.getValue().toCharArray()[1] - '0',
					boxKbPred.getValue().toCharArray()[3] - '0');
			goalPos = new Position2D(top.getValue().toCharArray()[1] - '0', top.getValue().toCharArray()[3] - '0');
			SearchablePlayer ps = new SearchablePlayer(playerTempPos, boxTempPos, level);
			ps.setBoxesPos(boxesPos);
			searchable.setFromPos(boxTempPos);
			searchable.setToPos(goalPos);
			searchable.setSearcher(bfs);
			searchable.setPlayerPos(playerTempPos);
			searchable.setBoxesPos(boxesPos);
			searchable.setSearchableBoxPos(boxTempPos);

			Solution solution = bfs.search(searchable);

			if (solution != null) {
				playerPos = (Position2D) bfs.getFinalState().getCameFrom().getState();
				boxesPos.remove(boxTempPos);
				boxesPos.add(goalPos);
				satisfying = new HashSet<>();
				PlannableAction planAction = new PlannableAction("MoveBox", top.getId(), top.getValue());
				planAction.setEffects(new Clause(new SokoPredicate("BoxAt", planAction.getId(), planAction.getValue()),
						new SokoPredicate("PlayerAt", "Player1", playerPos.toString()),
						new SokoPredicate("ClearAt", playerTempPos.toString(), playerTempPos.toString())));
				planAction.setPreConditions(new Clause(new SokoPredicate("ClearAt", top.getValue(), top.getValue())));
				planAction.setSubActions(solution.getActions());
				satisfying.add(planAction);
			} else
				return null;
		}

		return satisfying;
	}

}
