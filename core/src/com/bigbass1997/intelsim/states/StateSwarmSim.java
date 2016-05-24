package com.bigbass1997.intelsim.states;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass1997.intelsim.skins.SkinManager;
import com.bigbass1997.intelsim.world.World;
import com.bigbass1997.intelsim.world.swarm.Boid;
import com.bigbass1997.intelsim.world.swarm.BoidManager;

public class StateSwarmSim extends State {

	private ShapeRenderer sr;
	
	private Label infoLabel;
	
	private BoidManager boidManager;
	
	private Random rand = new Random();
	
	public StateSwarmSim(String id, final StateManager managerRef){
		super(id, managerRef);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() * 2, Gdx.graphics.getHeight() * 2);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		
        world = new World(cam);
        
		stage = new Stage();
		
		infoLabel = new Label("", SkinManager.getSkin("fonts/computer.ttf", 24));
		stage.addActor(infoLabel);
		
		sr = new ShapeRenderer(50000);
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		
		boidManager = new BoidManager(cam);
	}
	
	@Override
	public void render() {
		sr.begin(ShapeType.Filled);
		boidManager.render(sr);
		sr.end();
		
		world.render();
		stage.draw();
	}
	
	@Override
	public void update(float delta) {
		Input input = Gdx.input;
		
		if(input.isKeyJustPressed(Keys.M)){
			float mx = input.getX();
			float my = -input.getY() + Gdx.graphics.getHeight();
			
			boidManager.boids.add(new Boid(mx, my, rand.nextInt(5000)-2500, rand.nextInt(5000)-2500, rand.nextInt(10) + 6));
		} else if(input.isKeyPressed(Keys.N)){
			float mx = input.getX();
			float my = -input.getY() + Gdx.graphics.getHeight();
			
			boidManager.boids.add(new Boid(mx, my, rand.nextInt(5000)-2500, rand.nextInt(5000)-2500, rand.nextInt(10) + 6));
		} else if(input.isKeyPressed(Keys.B)){
			boidManager.boids.add(new Boid(rand.nextInt((int) cam.viewportWidth), rand.nextInt((int) cam.viewportHeight), (rand.nextInt(5000)-2500) * 10, (rand.nextInt(5000)-2500) * 10, rand.nextInt(10) + 6));
		}
		
		if(input.isKeyJustPressed(Keys.C)){
			boidManager.boids.clear();
		}
		
		//if(input.isKeyJustPressed(Keys.S))
		//	SwarmUtil.updatePositions(boids);
		
		boidManager.update(delta);
		
		String n = "\n";
		String info = 
				"Data:" + n +
				"  FPS: " + Gdx.graphics.getFramesPerSecond() + n +
				"  Boids: " + boidManager.boids.size();
		
		infoLabel.setText(info);
		infoLabel.setPosition(10, Gdx.graphics.getHeight() - (infoLabel.getPrefHeight() / 2) - 5);
		
		world.update(delta);
		stage.act(delta);
	}
}
