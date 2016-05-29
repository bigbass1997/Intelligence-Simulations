package com.bigbass1997.intelsim.util;

import com.badlogic.gdx.InputAdapter;

public abstract class ScrollwheelInputAdapter extends InputAdapter {
	
	@Override
	public abstract boolean scrolled(int amount);
}
