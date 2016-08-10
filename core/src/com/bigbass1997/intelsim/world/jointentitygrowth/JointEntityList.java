package com.bigbass1997.intelsim.world.jointentitygrowth;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class JointEntityList {
	
	public static enum EntitySide {
		TOP(0), BOTTOM(1), LEFT(2), RIGHT(3);
		
		private final int id;
		EntitySide(int id){
			this.id = id;
		}
		
		public int getValue(){
			return id;
		}
	}
	
	public JointEntity[] attachedEntities;
	
	public JointEntity parent;
	
	public JointEntityList(JointEntity parent){
		attachedEntities = new JointEntity[4];
	}
	
	public void render(ShapeRenderer sr){
		for(int i = 0; i < 4; i++){
			if(attachedEntities[i] != null){
				attachedEntities[i].render(sr);
			}
		}
	}
	
	public void update(float delta, Camera cam){
		for(int i = 0; i < 4; i++){
			if(attachedEntities[i] != null) attachedEntities[i].update(delta, cam);
			switch(EntitySide.values()[i]){
			case TOP:
				if(attachedEntities[EntitySide.TOP.getValue()] != null) attachedEntities[EntitySide.TOP.getValue()].pos.set(parent.pos.x, parent.pos.y + parent.size);
				break;
			case BOTTOM:
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
			}
		}
	}

	public void addEntity(JointEntity entity, EntitySide side) {
		attachedEntities[side.getValue()] = entity;
	}
}
