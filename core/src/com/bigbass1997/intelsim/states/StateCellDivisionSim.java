package com.bigbass1997.intelsim.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass1997.intelsim.skins.SkinManager;
import com.bigbass1997.intelsim.world.World;
import com.bigbass1997.intelsim.world.cell.Cell;
import com.bigbass1997.intelsim.world.cell.CellManager;

public class StateCellDivisionSim extends State {

	private ShapeRenderer sr;
	
	private Label infoLabel;

	private float scalar = 10f;
	
	private CellManager cellManager;
	
	public StateCellDivisionSim(String id, final StateManager managerRef){
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
		
		cellManager = new CellManager();
		cellManager.cells.add(new Cell(cam.viewportWidth / 2, cam.viewportHeight / 2, 400, 0xFFFFFFFF));
	}
	
	@Override
	public void render() {
		sr.begin(ShapeType.Line);
		cellManager.render(sr);
		sr.end();
		
		world.render();
		stage.draw();
	}
	
	@Override
	public void update(float delta) {
		String n = "\n";
		String info = 
				"Data:" + n +
				"  FPS: " + Gdx.graphics.getFramesPerSecond() + n +
				"  Cells: " + cellManager.cells.size();
		
		infoLabel.setText(info);
		infoLabel.setPosition(10, Gdx.graphics.getHeight() - (infoLabel.getPrefHeight() / 2) - 5);
		
		cellManager.update(delta);
		world.update(delta);
		stage.act(delta);
	}
}
