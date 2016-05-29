package com.bigbass1997.intelsim.world.particlegrowth;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Particle {
	
	private float rotation = 0;
	
	public enum SHAPE {
		SQUARE, TRIANGLE, CIRCLE;
	}

	public Vector2 pos, vel;
	public Color color;
	public SHAPE shape;
	public boolean isMale;
	
	public float level = 5;
	
	public Particle(float posX, float posY, float velX, float velY, int color, SHAPE shape, boolean isMale){
		this.pos = new Vector2(posX, posY);
		this.vel = new Vector2(velX, velY);
		this.color = new Color(color);
		this.shape = shape;
		this.isMale = isMale;
	}
	
	public void render(ShapeRenderer sr){
		sr.set(ShapeType.Filled);
		sr.setColor(color);
		
		sr.identity();
		sr.translate(pos.x + (level / 2f), pos.y + (level / 2f), 0);
		sr.rotate(0, 0, 1, rotation);
		sr.translate(-(pos.x + (level / 2f)), -(pos.y + (level / 2f)), 0);
		switch(shape){
		case SQUARE:
			sr.rect(pos.x, pos.y, level, level);
			break;
		case TRIANGLE:
			sr.triangle(
					pos.x - (level / 2), pos.y - (level / 2),
					pos.x + (level / 2), pos.y - (level / 2),
					pos.x, pos.y + (level / 2)
			);
			break;
		case CIRCLE:
			sr.circle(pos.x, pos.y, level / 2);
			break;
		}
		sr.identity();
	}
	
	public void update(float delta, Camera cam){
		rotation = (MathUtils.atan2(vel.y, vel.x) * MathUtils.radiansToDegrees) - 90;
		
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
