package com.bigbass1997.intelsim.world.cell;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CellManager {
	
	public ArrayList<Cell> cells;
	
	public CellManager(){
		cells = new ArrayList<Cell>();
	}
	
	public void render(ShapeRenderer sr){
		for(Cell cell : cells){
			cell.render(sr);
		}
	}
	
	public void update(float delta){
		if(Gdx.input.isKeyJustPressed(Keys.D)){
			ArrayList<Cell> tmp = new ArrayList<Cell>();
			for(Cell cell : cells){
				tmp.add(cell.divide());
				tmp.add(cell.divide());
			}
			cells.clear();
			cells.addAll(tmp);
			tmp.clear();
		}
		
		for(Cell cell : cells){
			cell.update(delta);
			
			cell.x += ((new Random()).nextFloat() * 100) - 50;
			cell.y += ((new Random()).nextFloat() * 100) - 50;
		}
	}
}
