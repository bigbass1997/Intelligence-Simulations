package com.bigbass1997.intelsim.world.jointentitygrowth;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class JointEntityManager {
	
	private Random rand;
	
	private ArrayList<JointEntity> jointEntities;
	
	public JointEntityManager(){
		rand = new Random();
		jointEntities = new ArrayList<JointEntity>();
	}
	
	public JointEntity addRandomJointEntity(int color, Camera cam){
		SquareJointEntity jointEntity = new SquareJointEntity(rand.nextFloat() * cam.viewportWidth, rand.nextFloat() * cam.viewportHeight, (rand.nextFloat() * 50) - 25, (rand.nextFloat() * 50) - 25, color);
		jointEntities.add(jointEntity);
		return jointEntity;
	}
	
	public JointEntity addJointEntity(float posx, float posy, float velx, float vely, int color){
		SquareJointEntity jointEntity = new SquareJointEntity(posx, posy, velx, vely, color);
		jointEntities.add(jointEntity);
		return jointEntity;
	}
	
	public void render(ShapeRenderer sr){
		for(JointEntity jointEntity : jointEntities){
			jointEntity.render(sr);
		}
	}

	public void update(float delta, Camera cam) {
		for(JointEntity jointEntity : jointEntities){
			jointEntity.update(delta, cam);
			jointEntity.isColliding = false;
			
			for(JointEntity otherEntity : jointEntities){
				if(!jointEntity.equalsJointEntity(otherEntity)){
					if(jointEntity.intersects(otherEntity)){
						
						//later
					}
				}
			}
		}
	}
}
