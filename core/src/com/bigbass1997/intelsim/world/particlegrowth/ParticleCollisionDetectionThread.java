package com.bigbass1997.intelsim.world.particlegrowth;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class ParticleCollisionDetectionThread extends Thread {
	
	private ArrayList<Particle> particlesRef;
	
	public ParticleCollisionDetectionThread(ArrayList<Particle> particlesRef){
		this.particlesRef = particlesRef;
	}
	
	@Override
	public void run() {
		while(true){
			for(Particle particle : particlesRef){
				for(Particle particleToCheck : particlesRef){
					if(particle.intersects(particleToCheck)){
						particle.pos.x -= particle.vel.x * Gdx.graphics.getDeltaTime();
						particle.pos.y -= particle.vel.y * Gdx.graphics.getDeltaTime();
					}
				}
			}
		}
	}
}
