package com.bigbass1997.intelsim.world.swarm;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bigbass1997.intelsim.util.SwarmUtil;

public class BoidManager {
	
	public ArrayList<Boid> boids;
	
	private Camera cam;
	
	public BoidManager(Camera cam){
		this.cam = cam;
		
		boids = new ArrayList<Boid>();
	}
	
	public void update(float delta){
		for(Boid boid : boids){
			SwarmUtil.updatePositions(boid, boids);
			
			boid.update(delta, cam.viewportWidth, cam.viewportHeight);
		}
	}
	
	public void render(ShapeRenderer sr){
		for(Boid boid : boids){
			boid.render(sr);
		}
	}
}
