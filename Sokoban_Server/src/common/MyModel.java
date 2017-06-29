/**
* This class is the game model
* @author Maayan & Eden
*/

package common;

import java.io.IOException;
import java.util.Observable;

import common.data.Actor;
import common.data.Box;
import common.data.Item;
import common.data.Level2D;
import common.data.Position2D;
import common.policy.MySokobanPolicy;

public class MyModel extends Observable implements Model {

	Level2D _lvl;
	MySokobanPolicy _msp;

	public Level2D getLevel() {
		return _lvl;
	}

	public void setLevel(Level2D lvl) {
		_lvl = new Level2D(lvl);
	}

	public MySokobanPolicy getPolicy() {
		return _msp;
	}

	public boolean move(Position2D dest) {
		Actor actor = (Actor) _lvl.getActors().get(0);
		Position2D old = actor.getPos();

		_msp = new MySokobanPolicy(old, getLevel());

		if (!(_msp.isFinished())) {

			if (_msp.check(dest)) { // our POLICY
				Item itmInDest = getLevel().getItemInPlace(dest);// destination
																	// item
				dest.setWasTarget(getLevel().getItemInPlace(itmInDest.getPos()).getPos().isWasTarget());

				if ((itmInDest.getType().compareTo("Box")) == 0) {
					Position2D boxDest = new Position2D(_msp.newPos(dest));
					boxDest.setWasTarget(getLevel().getItemInPlace(boxDest).getPos().isWasTarget());
					((Box) itmInDest).move(dest, boxDest, getLevel());
				}
				actor.move(old, dest, getLevel());

				this._lvl.setCounter(this._lvl.getCounter() + 1);
				return true;
			}
		}
		return false;
	}

	@Override
	public void exit() {
		System.out.close();

		try {
			System.in.close();
			System.exit(0);
		} catch (IOException e) {
		}

	}

}
