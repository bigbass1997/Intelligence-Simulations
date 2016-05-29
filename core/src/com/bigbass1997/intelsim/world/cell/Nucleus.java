package com.bigbass1997.intelsim.world.cell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Nucleus {
	
	public float x, y, radius;
	public Color color;
	
	public Nucleus(float x, float y, float radius, int color){
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = new Color(color);
	}
	
	public void render(ShapeRenderer sr){
		sr.setColor(color);
		sr.circle(x, y, radius);
	}
	
	public void update(float delta){
		
	}
}
