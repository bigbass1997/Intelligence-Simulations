package com.bigbass1997.intelsim.world.jointentitygrowth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.intelsim.util.MathUtilExtremes;

public class SquareJointEntity extends JointEntity {
	
	public SquareJointEntity(float posX, float posY, float velX, float velY, int color){
		super(posX, posY, velX, velY, color);
		
		this.velMax = new Vector2(40,40);
	}
	
	@Override
	public void render(ShapeRenderer sr){
		this.rotateAboutCenter(sr, rotation);
		
		sr.set(ShapeType.Filled);
		sr.setColor(color);
		sr.rect(pos.x, pos.y, size, size);
		
		
		if(Gdx.input.isKeyPressed(Keys.F)){
			sr.setColor(Color.CYAN);
			sr.line(pos.x, pos.y, pos.x, pos.y + 5);
		}
		
		sr.identity();
		
		if(Gdx.input.isKeyPressed(Keys.D)){ // Hold 'D' to render debug velocity lines
			sr.setColor(0, 1, 0, 1);
			sr.rectLine(pos.x, pos.y, pos.x + (vel.x), pos.y, 2);
			sr.setColor(0, 0, 1, 1);
			sr.rectLine(pos.x, pos.y, pos.x, pos.y + (vel.y), 2);
		}
	}
	
	@Override
	public void update(float delta, Camera cam){
		rotation = (MathUtils.atan2(vel.y, vel.x) * MathUtils.radiansToDegrees) - 90;
		
		vel.x = MathUtilExtremes.closestToZero(vel.x, velMax.x);
		vel.y = MathUtilExtremes.closestToZero(vel.y, velMax.y);
		
		pos.x += vel.x * delta;
		pos.y += vel.y * delta;
		
		if(pos.x < 0 && vel.x < 0){
			pos.x = cam.viewportWidth;
		}
		if(pos.x > cam.viewportWidth && vel.x > 0){
			pos.x = 0;
		}
		
		if(pos.y < 0 && vel.y < 0){
			pos.y = cam.viewportHeight;
		}
		if(pos.y > cam.viewportHeight && vel.y > 0){
			pos.y = 0;
		}
	}
}
