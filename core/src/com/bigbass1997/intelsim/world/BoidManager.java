package com.bigbass1997.intelsim.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bigbass1997.intelsim.util.SwarmUtil;

public class BoidManager {
	
	public ArrayList<Boid> boids;
	
	public BoidManager(){
		boids = new ArrayList<Boid>();
	}
	
	public void update(float delta){
		for(Boid boid : boids){
			SwarmUtil.updatePositions(boid, boids);
			
			boid.update(delta);
		}
	}
	
	public void render(ShapeRenderer sr){
		for(Boid boid : boids){
			boid.render(sr);
		}
	}
}
