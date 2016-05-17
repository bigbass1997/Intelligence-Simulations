package com.bigbass1997.intelsim.world.swarm;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.intelsim.states.StateSwarmSim;

public class Boid {
	
	private static Random rand = new Random();
	
	private static int count = 0;

	public final float minBound = 10, maxBound = 50;
	
	public String id = String.valueOf((count += 1));
	
	public Vector2 pos, vel, acc;
	
	public float radius;
	
	public Color color;
	
	public Boid(float xPos, float yPos, float xVel, float yVel, float radius){
		this.pos = new Vector2(xPos, yPos);
		this.vel = new Vector2(xVel, yVel);
		this.acc = new Vector2(0, 0);
		this.radius = radius;
		
		this.color = new Color(0xFF0000FF);
		
		color.r = rand.nextFloat();
		color.g = rand.nextFloat();
		color.b = rand.nextFloat();
	}
	
	public void render(ShapeRenderer sr){
		if(Gdx.input.isKeyPressed(Keys.D)){
			sr.setColor(0, 1, 0, 1);
			sr.rectLine(pos.x, pos.y, pos.x + (vel.x), pos.y, 2);
			sr.setColor(0, 0, 1, 1);
			sr.rectLine(pos.x, pos.y, pos.x, pos.y + (vel.y), 2);
		}

		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			ShapeType prev = sr.getCurrentType();
			sr.set(ShapeType.Line);
			sr.setColor(1,1,1,1);
			sr.circle(pos.x, pos.y, maxBound, 30);
			sr.circle(pos.x, pos.y, minBound, 30);
			sr.set(prev);
		}
		
		sr.setColor(1, 1, 1, 0.2f);
		sr.rectLine(pos.x, pos.y, pos.x + (vel.nor().x * (radius + 8)), pos.y + (vel.nor().y * (radius + 8)), 2);
		
		sr.setColor(color);
		sr.circle(pos.x, pos.y, radius, 30);
	}
	
	public void update(float delta, float screenWidth, float screenHeight){
		vel.x += acc.x;
		vel.y += acc.y;
		
		pos.x += vel.x * delta;
		pos.y += vel.y * delta;
		
		float dif = 0;
		
		if(pos.x > screenWidth - dif) pos.x = dif;
		if(pos.x < dif) pos.x = screenWidth - dif;
		
		if(pos.y > screenHeight - dif) pos.y = dif;
		if(pos.y < dif) pos.y = screenHeight - dif;
	}
	
	public boolean equals(Boid b){
		if(b == this || b.id.equals(this.id)){
			return true;
		}
		
		return false;
	}
}
