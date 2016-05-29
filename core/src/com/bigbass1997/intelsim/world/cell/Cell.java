package com.bigbass1997.intelsim.world.cell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Cell {
	
	public float x, y, radius;
	public Color color;
	
	private Nucleus nucleus;
	
	public Cell(float x, float y, float radius, int color){
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = new Color(color);
		
		nucleus = new Nucleus(x + radius, y + radius, radius / 4, color);
	}
	
	public void render(ShapeRenderer sr){
		sr.setColor(color);
		sr.circle(x + radius, y + radius, radius);
		
		nucleus.render(sr);
	}
	
	public void update(float delta){
		nucleus.x = x + radius;
		nucleus.y = y + radius;
	}
	
	public Cell clone(){
		return new Cell(x, y, radius, Color.argb8888(color));
	}
	
	public Cell divide(){
		return new Cell(x, y, radius * 0.750f, Color.argb8888(color));
	}
}
