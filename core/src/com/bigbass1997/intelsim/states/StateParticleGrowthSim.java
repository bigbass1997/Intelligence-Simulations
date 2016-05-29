package com.bigbass1997.intelsim.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass1997.intelsim.skins.SkinManager;
import com.bigbass1997.intelsim.world.World;
import com.bigbass1997.intelsim.world.particlegrowth.Particle;

public class StateParticleGrowthSim extends State {

	private ShapeRenderer sr;
	
	private Label infoLabel;

	private float scalar = 0.5f;
	
	private Particle testParticle;
	
	public StateParticleGrowthSim(String id, final StateManager managerRef){
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
		
		testParticle = new Particle(50, 50, 10, 10, 0xFFFFFFFF, Particle.SHAPE.SQUARE, true);
	}
	
	@Override
	public void render() {
		sr.begin(ShapeType.Line);
		testParticle.render(sr);
		sr.end();
		
		world.render();
		stage.draw();
	}
	
	@Override
	public void update(float delta) {
		if(Gdx.input.isKeyPressed(Keys.UP)){
			changeCameraViewport(0.01f);
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			changeCameraViewport(-0.01f);
		}
		
		testParticle.update(delta, cam);
		
		String n = "\n";
		String info = 
				"Data:" + n +
				"  FPS: " + Gdx.graphics.getFramesPerSecond();
		
		infoLabel.setText(info);
		infoLabel.setPosition(10, Gdx.graphics.getHeight() - (infoLabel.getPrefHeight() / 2) - 5);
		
		world.update(delta);
		stage.act(delta);
	}
	
	private void changeCameraViewport(float dscalar){
		scalar += dscalar;
		
		cam.viewportWidth = Gdx.graphics.getWidth() * scalar;
		cam.viewportHeight = Gdx.graphics.getHeight() * scalar;
		cam.update();

		sr.setProjectionMatrix(cam.combined);
		
		System.out.println(scalar + " | " + cam.viewportWidth + " | " + cam.viewportHeight);
	}
}
