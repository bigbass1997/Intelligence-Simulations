package com.bigbass1997.intelsim.world;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
		sr.setColor(1, 1, 1, 0.2f);
		sr.rectLine(pos.x, pos.y, pos.x + (vel.nor().x * (radius + 8)), pos.y + (vel.nor().y * (radius + 8)), 2);
		
		sr.setColor(color);
		sr.circle(pos.x, pos.y, radius, 30);
	}
	
	public void update(float delta){
		int swidth = (int) StateSwarmSim.tmpCamViewpointWidth;
		int sheight = (int) StateSwarmSim.tmpCamViewpointHeight;
		
		vel.x += acc.x;
		vel.y += acc.y;
		
		pos.x += vel.x * delta * 2;
		pos.y += vel.y * delta * 2;
		
		float dif = 0;
		
		if(pos.x > swidth - dif) pos.x = dif;
		if(pos.x < dif) pos.x = swidth - dif;
		
		if(pos.y > sheight - dif) pos.y = dif;
		if(pos.y < dif) pos.y = sheight - dif;
	}
	
	public boolean equals(Boid b){
		if(b == this){
			return true;
		}
		
		if(b.id.equals(this.id)){
			return true;
		}
		
		return false;
	}
}
