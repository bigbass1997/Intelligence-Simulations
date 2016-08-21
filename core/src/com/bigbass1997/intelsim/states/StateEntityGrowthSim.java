package com.bigbass1997.intelsim.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass1997.intelsim.skins.SkinManager;
import com.bigbass1997.intelsim.util.ScrollwheelInputAdapter;
import com.bigbass1997.intelsim.world.entitygrowth.SquareManager;

public class StateEntityGrowthSim extends State {

	private ShapeRenderer sr;
	
	private Label infoLabel;

	private float scalar = 1.00000f;
	
	private SquareManager squareManager;
	
	public StateEntityGrowthSim(String id, final StateManager managerRef){
		super(id, managerRef);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() * scalar, Gdx.graphics.getHeight() * scalar);
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		
		stage = new Stage();
		
		infoLabel = new Label("", SkinManager.getSkin("fonts/computer.ttf", 24));
		stage.addActor(infoLabel);
		
		sr = new ShapeRenderer(50000);
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		
		InputMultiplexer multInput = new InputMultiplexer();
		multInput.addProcessor(stage);
		multInput.addProcessor(new ScrollwheelInputAdapter(){
			@Override
			public boolean scrolled(int amount) {
				if(amount == 1){
					changeCameraViewport(0.05f);
				} else if(amount == -1){
					changeCameraViewport(-0.05f);
				}
				return true;
			}
		});
		
		Gdx.input.setInputProcessor(multInput);
		
		squareManager = new SquareManager();
		
		for(int i = 0; i < 50; i++){ // Randomly create x number of Joint Entities
			squareManager.addRandomSquare(0xFFFFFFFF, cam);
		}
	}
	
	@Override
	public void render() {
		sr.begin(ShapeType.Line);
		sr.setColor(Color.WHITE);
		sr.rect(0, 0, cam.viewportWidth, cam.viewportHeight);
		
		squareManager.render(sr);
		
		sr.end();
		
		stage.draw();
	}
	
	@Override
	public void update(float delta) {
		squareManager.update(delta, cam);
		
		String n = "\n";
		String info = 
				"Data:" + n +
				"  FPS: " + Gdx.graphics.getFramesPerSecond() + n +
				"  Scalar: " + scalar;
		
		infoLabel.setText(info);
		infoLabel.setPosition(10, Gdx.graphics.getHeight() - (infoLabel.getPrefHeight() / 2) - 5);
		
		stage.act(delta);
	}
	
	private void changeCameraViewport(float dscalar){
		scalar += dscalar;
		
		cam.viewportWidth = Gdx.graphics.getWidth() * scalar;
		cam.viewportHeight = Gdx.graphics.getHeight() * scalar;
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();

		sr.setProjectionMatrix(cam.combined);
	}
}
