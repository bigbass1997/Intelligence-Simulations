package com.bigbass1997.intelsim.world.jointentitygrowth;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class JointEntityList {
	
	public JointEntity[] attachedEntities;
	
	public JointEntity parent;
	
	public JointEntityList(JointEntity parent){
		attachedEntities = new JointEntity[4];
		
		this.parent = parent;
	}
	
	public void render(ShapeRenderer sr){
		for(int i = 0; i < 4; i++){
			if(attachedEntities[i] != null){
				attachedEntities[i].render(sr);
			}
		}
	}
	
	public void update(float delta, Camera cam, ArrayList<JointEntity> otherEntities){
		for(int i = 0; i < 4; i++){
			if(attachedEntities[i] != null){
				attachedEntities[i].update(delta, cam, otherEntities);
			}
			
			updateSidePos(i);
		}
	}
	
	private void updateSidePos(int side){
		JointEntity tmp = attachedEntities[side];
		
		if(tmp != null){
			switch(side){
			case EntitySide.TOP:
				tmp.pos.set(parent.pos.x, parent.pos.y + parent.size);
				break;
			case EntitySide.BOTTOM:
				tmp.pos.set(parent.pos.x, parent.pos.y - tmp.size);
				break;
			case EntitySide.LEFT:
				tmp.pos.set(parent.pos.x - tmp.size, parent.pos.y);
				break;
			case EntitySide.RIGHT:
				tmp.pos.set(parent.pos.x + parent.size, parent.pos.y);
				break;
			}
		}
	}

	public boolean addEntity(JointEntity entity, int side) {
		if(attachedEntities[side] == null){
			attachedEntities[side] = entity;
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<JointEntity> getAllAttachedEntities(){
		ArrayList<JointEntity> list = new ArrayList<JointEntity>();
		for(int i = 0; i < 4; i++){
			if(attachedEntities[i] != null){
				list.add(attachedEntities[i]);
			}
		}
		return list;
	}
}
