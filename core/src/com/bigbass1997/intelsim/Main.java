package com.bigbass1997.intelsim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.bigbass1997.intelsim.fonts.FontManager;
import com.bigbass1997.intelsim.ScreenshotFactory;
import com.bigbass1997.intelsim.states.State;
import com.bigbass1997.intelsim.states.StateMainMenu;
import com.bigbass1997.intelsim.states.StateManager;

public class Main extends ApplicationAdapter {
	
	public boolean isScreenshotReady = true;
	
	public StateManager stateManager;
	
	@Override
	public void create () {
		FontManager.addFont("fonts/computer.ttf", new int[]{16}); //Added font to be used with Debug Text
		
		stateManager = new StateManager(new State("init"));
		stateManager.setCurState(new StateMainMenu("MainMenu", stateManager));
	}

	@Override
	public void render () {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		stateManager.render();
		
		//UPDATE
		update();
	}
	
	private void update(){
		Input input = Gdx.input;
		float delta = Gdx.graphics.getDeltaTime();
		
		stateManager.update(delta);
		
		if(input.isKeyPressed(Keys.Z) && isScreenshotReady){
			ScreenshotFactory.saveScreen();
			isScreenshotReady = false;
		} else if(!input.isKeyPressed(Keys.Z) && !isScreenshotReady){
			isScreenshotReady = true;
		}
	}
	
	@Override
	public void dispose(){
		stateManager.dispose();
	}
}
