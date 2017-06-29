package sokobanSolver;

import stripsLib.Predicate;

public class SokoPredicate extends Predicate {
	public SokoPredicate(String type, String id, String value) {
		super(type, id, value);
	}

	@Override
	public boolean contradicts(Predicate predicate) {
		return super.contradicts(predicate) || (!id.equals(predicate.getId()) && value.equals(predicate.getValue())
				|| (value.equals(predicate.getValue()) && (!type.equals(predicate.getType()))));

	}
}
