package com.bigbass1997.intelsim.world.jointentitygrowth;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass1997.intelsim.skins.SkinManager;

public class JointEntity {
	
	private static int count = 0;
	public String id = String.valueOf((count += 1));
	
	protected float rotation = 0;
	
	public boolean isColliding = false;

	public Vector2 pos, vel, velMax;
	public Color color;
	
	public float size = 25f;
	
	public JointEntityList jointList;
	
	protected Stage stage;
	
	protected Container<Label> idLabelWrapper;
	private Label idLabel;
	
	
	public JointEntity(float posX, float posY, float velX, float velY, int color){
		this.pos = new Vector2(posX, posY);
		this.vel = new Vector2(velX, velY);
		this.color = new Color(color);
		
		jointList = new JointEntityList(this);

		stage = new Stage();
		
		idLabel = new Label(id, SkinManager.getSkin("fonts/computer.ttf", 24));
		idLabel.setColor(Color.RED);
		idLabelWrapper = new Container<Label>(idLabel);
		idLabelWrapper.setTransform(true);
		idLabelWrapper.setOrigin(0, 0);
		idLabelWrapper.setScale(0.75f);
		
		stage.addActor(idLabelWrapper);
	}
	
	public void render(ShapeRenderer sr) {
	}
	
	public void renderDebug(ShapeRenderer sr){
		sr.set(ShapeType.Line);
		sr.setColor(Color.RED);
		
		this.rotateAboutCenter(sr, rotation);
		
		sr.rect(pos.x, pos.y, size, size);
		
		sr.identity();
	}

	public void update(float delta, Camera cam, ArrayList<JointEntity> otherEntities) {
	}
	
	/**
	 * Attempts to join two entities (and their already joined entities) together.
	 * 
	 * @param entity other entity to adjoin to
	 */
	public boolean adjoinWithEntity(JointEntity entity, int side){
		return jointList.addEntity(entity, side);
	}
	
	/**
	 * Determines and marks if passed entity is intersecting this entity.
	 * 
	 * @param entity JointEntity to compare with
	 * @return true if passed entity intersects with this entity
	 */
	public boolean intersects(JointEntity entity){
		if(entity.pos.x > pos.x + size || entity.pos.y > pos.y + size || pos.x > entity.pos.x + entity.size || pos.y > entity.pos.y + entity.size || this.isChildOf(entity)){
			return false;
		}
		
		isColliding = true;
		entity.isColliding = true;
		return true;
	}
	
	public boolean isChildOf(JointEntity parent){
		return parent.jointList.getAllAttachedEntities().contains(this);
	}
	
	public int onWhatSide(JointEntity entity){
		Vector2 entCenter = entity.getCenter();
		Vector2 thisCenter = getCenter();
		
		if(entCenter.x >= pos.x - (size / 2) && entCenter.x <= pos.x + size + (size / 2)){ // TOP or BOTTOM 
			if(entCenter.y > thisCenter.y){ // TOP
				return EntitySide.TOP;
			} else if(entCenter.y <= thisCenter.y){ //BOTTOM
				return EntitySide.BOTTOM;
			}
		} else if(entCenter.y >= pos.y - (size / 2) && entCenter.y <= pos.y + size + (size / 2)){ // LEFT or RIGHT
			if(entCenter.x < thisCenter.x){ // LEFT
				return EntitySide.LEFT;
			} else if(entCenter.x >= thisCenter.x){ // RIGHT
				return EntitySide.RIGHT;
			}
		}
		
		return -1;
	}
	
	/**
	 * Determines if passed entity is closer to this entity than the range.
	 * 
	 * @param entity JointEntity to compare with
	 * @param range distance in pixels
	 * @return true if passed entity is closer to this entity than range
	 */
	public boolean withinRange(JointEntity entity, float range){
		return (pos.dst(entity.pos) < range);
	}
	
	/**
	 * Determines if passed entity is further away from this entity than the range.
	 * 
	 * @param entity JointEntity to compare with
	 * @param range distance in pixels
	 * @return true if passed entity is further from this entity than range
	 */
	public boolean outsideRange(JointEntity entity, float range){
		return (pos.dst(entity.pos) > range);
	}
	
	/**
	 * Determines if passed entity equals this entity based on its unique id number.
	 * Id number is assigned and increased by 1 when each entity is created.
	 * 
	 * @param entity JointEntity to compare with
	 * @return true if passed entity equals this entity
	 */
	public boolean equalsJointEntity(JointEntity entity) {
		return (this.id.equals(entity.id));
	}
	
	public Vector2 getCenter(){
		return new Vector2(pos.x + (size / 2), pos.y + (size / 2));
	}
	
	/**
	 * Rotates the entity about its center.
	 * 
	 * @param sr ShapeRenderer from render method
	 * @param rotation rotation in degrees
	 */
	protected void rotateAboutCenter(ShapeRenderer sr, float rotation){
		sr.identity();
		sr.translate(pos.x + (size / 2f), pos.y + (size / 2f), 0);
		sr.rotate(0, 0, 1, rotation);
		sr.translate(-(pos.x + (size / 2f)), -(pos.y + (size / 2f)), 0);
	}
}
