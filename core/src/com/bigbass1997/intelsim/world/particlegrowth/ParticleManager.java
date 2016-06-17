package com.bigbass1997.intelsim.world.particlegrowth;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class ParticleManager {
	
	private Random rand;
	
	private ArrayList<Particle> particles;
	private ArrayList<Particle> particlesToRemove;
	
	public ParticleManager(){
		rand = new Random();
		particles = new ArrayList<Particle>();
		particlesToRemove = new ArrayList<Particle>();
	}
	
	public Particle addRandomParticle(int color, Camera cam){
		Particle particle = new Particle(rand.nextFloat() * cam.viewportWidth, rand.nextFloat() * cam.viewportHeight, (rand.nextFloat() * 50) - 25, (rand.nextFloat() * 50) - 25, color, Particle.SHAPE.random(), rand.nextBoolean());
		particles.add(particle);
		return particle;
	}
	
	public Particle addParticle(float posx, float posy, float velx, float vely, int color, Particle.SHAPE shape, boolean isMale){
		Particle particle = new Particle(posx, posy, velx, vely, color, shape, isMale);
		particles.add(particle);
		return particle;
	}
	
	public void render(ShapeRenderer sr){
		for(Particle particle : particles){
			particle.render(sr);
		}
	}

	public void update(float delta, Camera cam) {
		for(Particle particle : particles){
			particle.update(delta, cam);
			
			Vector2 dv = new Vector2(0,0);
			
			for(Particle particleToCheck : particles){
				if(particle.intersects(particleToCheck)){
					particle.isColliding = true;
					particleToCheck.isColliding = true;
				}
				
				if(particle.withinRange(particleToCheck, particle.upperRange) && particle.outsideRange(particleToCheck, particle.lowerRange) && !particle.equalsParticle(particleToCheck)){
					
					dv.x += particleToCheck.pos.x - particle.pos.x;
					dv.y += particleToCheck.pos.y - particle.pos.y;
					
					/*System.out.println(particle.vel + " | " + MathUtilExtremes.farthestFromZero(particle.vel.x, particle.vel.y));
					if(particle.vel.x == MathUtilExtremes.closestToZero(particle.vel.x, particle.vel.y)){
						particle.pos.x -= particleToCheck.vel.x * delta * 1f;
					}
					if(particle.vel.y == MathUtilExtremes.closestToZero(particle.vel.x, particle.vel.y)){
						particle.pos.y -= particleToCheck.vel.y * delta * 1f;
					}*/
				}
			}
			
			dv.x /= particles.size();
			dv.y /= particles.size();
			dv.x *= -1;
			dv.y *= -1;
			dv.nor();
			
			particle.vel.x += dv.x * 0.325f;
			particle.vel.y += dv.y * 0.325f;
			
			if(particle.health <= 0){
				particlesToRemove.add(particle);
			}
		}
		
		if(!particlesToRemove.isEmpty()){
			particles.removeAll(particlesToRemove);
			particlesToRemove.clear();
		}
	}
}
