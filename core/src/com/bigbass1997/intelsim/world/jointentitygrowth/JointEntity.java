package com.bigbass1997.intelsim.world.jointentitygrowth;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass1997.intelsim.skins.SkinManager;
import com.bigbass1997.intelsim.world.jointentitygrowth.JointEntityList.EntitySide;

public class JointEntity {
	
	private static int count = 0;
	public String id = String.valueOf((count += 1));
	
	protected float rotation = 0;
	
	public boolean isColliding = false;

	public Vector2 pos, vel, velMax;
	public Color color;
	
	public float size = 40f;
	
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
		idLabel.setColor(Color.GREEN);
		idLabelWrapper = new Container<Label>(idLabel);
		idLabelWrapper.setTransform(true);
		idLabelWrapper.setOrigin(0, 0);
		idLabelWrapper.setScale(1.5f);
		
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

	public void update(float delta, Camera cam) {
	}
	
	/**
	 * Attempts to join two entities (and their already joined entities) together.
	 * 
	 * @param entity other entity to adjoin to
	 */
	public void adjoinWithEntity(JointEntity entity, EntitySide side){
		jointList.addEntity(entity, side);
	}
	
	/**
	 * Determines and marks if passed entity is intersecting this entity.
	 * 
	 * @param entity JointEntity to compare with
	 * @return true if passed entity intersects with this entity
	 */
	public boolean intersects(JointEntity entity){
		if(entity.pos.x > pos.x + size || entity.pos.y > pos.y + size || pos.x > entity.pos.x + entity.size || pos.y > entity.pos.y + entity.size){
			return false;
		}
		
		isColliding = true;
		entity.isColliding = true;
		return true;
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
