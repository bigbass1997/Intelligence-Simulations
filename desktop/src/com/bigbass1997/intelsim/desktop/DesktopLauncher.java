package com.bigbass1997.intelsim.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bigbass1997.intelsim.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1200;
		config.height = 675;
		config.resizable = false;
		
		config.vSyncEnabled = false;
		
		config.title = "Intelligence Simulations";
		
		new LwjglApplication(new Main(), config);
	}
}
