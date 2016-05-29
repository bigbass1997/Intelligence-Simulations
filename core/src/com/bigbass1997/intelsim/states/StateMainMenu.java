package com.bigbass1997.intelsim.states;

import java.util.concurrent.Callable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.bigbass1997.intelsim.skins.SkinManager;
import com.bigbass1997.intelsim.world.InvokableButton;
import com.bigbass1997.intelsim.world.World;

public class StateMainMenu extends State {

	private ShapeRenderer sr;
	
	private Label infoLabel;
	
	private float scalar = 1f;
	
	public Table menu;
	
	public StateMainMenu(String id, final StateManager managerRef){
		super(id, managerRef);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() * scalar, Gdx.graphics.getHeight() * scalar);
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		
        world = new World(cam);
        
        stage = new Stage();
		
		menu = new Table(SkinManager.getSkin("fonts/computer.ttf", 24));
		menu.setSize(240, 400);
		menu.setPosition((Gdx.graphics.getWidth() / 2) - (menu.getWidth() / 2), 50);
		menu.align(Align.top);
		stage.addActor(menu);
		
        menu.add(new InvokableButton("Swarm Simulation", SkinManager.getSkin("fonts/computer.ttf", 24), (new Callable<Object>(){
			@Override
			public Object call() throws Exception {
				managerRef.setCurState(new StateSwarmSim("SwarmSim1", managerRef));
				return null;
			}
		}))).width(menu.getWidth()).height(36);

        menu.row();
        
        menu.add(new InvokableButton("Partical Growth Simulation", SkinManager.getSkin("fonts/computer.ttf", 24), (new Callable<Object>(){
			@Override
			public Object call() throws Exception {
				managerRef.setCurState(new StateParticleGrowthSim("ParticleGrowthSim1", managerRef));
				return null;
			}
		}))).width(menu.getWidth()).height(36);
        
        menu.row();
        
        menu.add(new InvokableButton("Cell Division Simulation", SkinManager.getSkin("fonts/computer.ttf", 24), (new Callable<Object>(){
			@Override
			public Object call() throws Exception {
				managerRef.setCurState(new StateCellDivisionSim("CellDivisionSim1", managerRef));
				return null;
			}
		}))).width(menu.getWidth()).height(36);
		
		infoLabel = new Label("", SkinManager.getSkin("fonts/computer.ttf", 24));
		stage.addActor(infoLabel);
		
		sr = new ShapeRenderer(50000);
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render() {
		sr.begin(ShapeType.Filled);
		//
		sr.end();
		
		world.render();
		stage.draw();
	}
	
	@Override
	public void update(float delta) {
		String n = "\n";
		String info = 
				"Data:" + n +
				"  FPS: " + Gdx.graphics.getFramesPerSecond();
		
		infoLabel.setText(info);
		infoLabel.setPosition(10, Gdx.graphics.getHeight() - (infoLabel.getPrefHeight() / 2) - 5);
		
		world.update(delta);
		stage.act(delta);
	}
}
