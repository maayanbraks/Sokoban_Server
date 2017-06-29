package common;

import common.policy.MySokobanPolicy;

public interface Model {

	public MySokobanPolicy getPolicy();

	public void exit();

}
