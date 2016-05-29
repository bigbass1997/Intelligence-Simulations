package com.bigbass1997.intelsim.world.particlegrowth;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ParticleManager {
	
	private Random rand;
	
	private ArrayList<Particle> particles;
	
	public ParticleManager(){
		rand = new Random();
		particles = new ArrayList<Particle>();
	}
	
	public Particle addRandomParticle(int color, Camera cam){
		Particle particle = new Particle(rand.nextFloat() * cam.viewportWidth, rand.nextFloat() * cam.viewportHeight, (rand.nextFloat() * 30) - 15, (rand.nextFloat() * 30) - 15, color, Particle.SHAPE.random(), rand.nextBoolean());
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
		}
	}
}
