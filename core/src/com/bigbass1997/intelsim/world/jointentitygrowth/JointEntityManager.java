package com.bigbass1997.intelsim.world.jointentitygrowth;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bigbass1997.intelsim.world.jointentitygrowth.JointEntityList.EntitySide;

public class JointEntityManager {
	
	private Random rand;
	
	private ArrayList<JointEntity> jointEntities;
	
	public JointEntityManager(){
		rand = new Random();
		jointEntities = new ArrayList<JointEntity>();
	}
	
	public JointEntity addRandomJointEntity(int color, Camera cam){
		SquareJointEntity jointEntity = new SquareJointEntity(rand.nextFloat() * cam.viewportWidth, rand.nextFloat() * cam.viewportHeight, (rand.nextFloat() * 100) - 50, (rand.nextFloat() * 100) - 50, color);
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
		for(int i = jointEntities.size() - 1; i >= 0; i--){
			JointEntity jointEntity = jointEntities.get(i);
			jointEntity.update(delta, cam);
			jointEntity.isColliding = false;
			
			for(int j = jointEntities.size() - 1; j >= 0; j--){
				JointEntity otherEntity = jointEntities.get(j);
				if(!jointEntity.equalsJointEntity(otherEntity)){
					if(jointEntity.intersects(otherEntity)){
						jointEntity.adjoinWithEntity(otherEntity, EntitySide.TOP);
						jointEntities.remove(j);
					}
				}
			}
		}
	}
}
