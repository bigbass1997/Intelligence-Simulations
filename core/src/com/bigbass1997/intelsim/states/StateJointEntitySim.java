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
import com.bigbass1997.intelsim.world.World;
import com.bigbass1997.intelsim.world.jointentitygrowth.JointEntityManager;

public class StateJointEntitySim extends State {

	private ShapeRenderer sr;
	
	private Label infoLabel;

	private float scalar = 1.00000f;
	
	private JointEntityManager jointEntityManager;
	
	public StateJointEntitySim(String id, final StateManager managerRef){
		super(id, managerRef);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() * scalar, Gdx.graphics.getHeight() * scalar);
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		
        world = new World(cam);
        
		stage = new Stage();
		
		infoLabel = new Label("", SkinManager.getSkin("fonts/computer.ttf", 24));
		stage.addActor(infoLabel);
		
		sr = new ShapeRenderer(50000);
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		
		jointEntityManager = new JointEntityManager();
		
		for(int i = 0; i < 100; i++){
			jointEntityManager.addRandomJointEntity(0xFFFFFFFF, cam);
		}
		
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
	}
	
	@Override
	public void render() {
		sr.begin(ShapeType.Line);
		sr.setColor(Color.WHITE);
		sr.rect(0, 0, cam.viewportWidth, cam.viewportHeight);
		
		jointEntityManager.render(sr);
		sr.end();
		
		world.render();
		stage.draw();
	}
	
	@Override
	public void update(float delta) {
		jointEntityManager.update(delta, cam);
		
		String n = "\n";
		String info = 
				"Data:" + n +
				"  FPS: " + Gdx.graphics.getFramesPerSecond() + n +
				"  Scalar: " + scalar;
		
		infoLabel.setText(info);
		infoLabel.setPosition(10, Gdx.graphics.getHeight() - (infoLabel.getPrefHeight() / 2) - 5);
		
		world.update(delta);
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
