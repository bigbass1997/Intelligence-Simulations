package com.bigbass1997.intelsim.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.bigbass1997.intelsim.util.SwarmUtil;

public class BoidManager {
	
	public ArrayList<Boid> boids;
	
	public BoidManager(){
		boids = new ArrayList<Boid>();
	}
	
	public void update(float delta){
		for(Boid boid : boids){
			boid.update(delta);
			
			SwarmUtil.updatePositions(boids);
		}
	}
	
	public void render(ShapeRenderer sr){
		for(Boid boid : boids){
			boid.render(sr);
			
			if(Gdx.input.isKeyPressed(Keys.SPACE)){
				ShapeType prev = sr.getCurrentType();
				sr.set(ShapeType.Line);
				sr.setColor(1,1,1,1);
				sr.circle(boid.pos.x, boid.pos.y, boid.maxBound, 30);
				sr.circle(boid.pos.x, boid.pos.y, boid.minBound, 30);
				sr.set(prev);
			}
		}
	}
}
