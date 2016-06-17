package com.bigbass1997.intelsim.world.particlegrowth;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.intelsim.util.MathUtilExtremes;

public class Particle {
	
	private static int count = 0;

	public String id = String.valueOf((count += 1));
	
	private float rotation = 0;
	
	public boolean isColliding = false;
	
	public enum SHAPE {
		SQUARE, TRIANGLE, CIRCLE;
		
		public static SHAPE random(){
			int val = (new Random()).nextInt(SHAPE.values().length);
			
			return SHAPE.values()[val];
		}
	}

	public Vector2 pos, vel, velMax;
	public Color color;
	public SHAPE shape;
	public boolean isMale;
	public float health = 100;
	public float hunger = 100;
	
	public float level = 5.0f;
	
	public float upperRange = 0, lowerRange = 0;
	
	public Particle(float posX, float posY, float velX, float velY, int color, SHAPE shape, boolean isMale){
		this.pos = new Vector2(posX, posY);
		this.vel = new Vector2(velX, velY);
		this.color = new Color(color);
		this.shape = shape;
		this.isMale = isMale;
		
		this.velMax = new Vector2(40,40);
	}
	
	public void render(ShapeRenderer sr){
		sr.identity();
		sr.translate(pos.x + (level / 2f), pos.y + (level / 2f), 0);
		sr.rotate(0, 0, 1, rotation);
		sr.translate(-(pos.x + (level / 2f)), -(pos.y + (level / 2f)), 0);
		
		if(Gdx.input.isKeyPressed(Keys.R)){
			sr.set(ShapeType.Line);
			sr.setColor(Color.WHITE);
			sr.circle(pos.x, pos.y, upperRange);
			sr.circle(pos.x, pos.y, lowerRange);
		}
		
		sr.set(ShapeType.Filled);
		sr.setColor(color);
		
		if(isColliding) sr.setColor(Color.RED);
		
		switch(shape){
		case SQUARE:
			sr.rect(pos.x - (level / 2), pos.y - (level / 2), level, level);
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
		
		if(Gdx.input.isKeyPressed(Keys.F)){
			sr.setColor(Color.CYAN);
			sr.line(pos.x, pos.y, pos.x, pos.y + level);
		}

		sr.setColor(Color.RED);
		sr.rect(pos.x - ((level * 1.5f) / 2), pos.y + (level / 1.8f) + (level / 10) + 1, level * 1.5f, level / 10);
		
		sr.setColor(Color.GREEN);
		sr.rect(pos.x - ((level * 1.5f) / 2), pos.y + (level / 1.8f) + (level / 10) + 1, level * 1.5f * (health / 100), level / 10);
		

		sr.setColor(Color.WHITE);
		sr.rect(pos.x - ((level * 1.5f) / 2), pos.y + (level / 1.8f), level * 1.5f, level / 10);
		
		sr.setColor(Color.ORANGE);
		sr.rect(pos.x - ((level * 1.5f) / 2), pos.y + (level / 1.8f), level * 1.5f * (hunger / 100), level / 10);
		
		sr.identity();
		
		if(Gdx.input.isKeyPressed(Keys.D)){
			sr.setColor(0, 1, 0, 1);
			sr.rectLine(pos.x, pos.y, pos.x + (vel.x), pos.y, 2);
			sr.setColor(0, 0, 1, 1);
			sr.rectLine(pos.x, pos.y, pos.x, pos.y + (vel.y), 2);
		}
	}
	
	public void update(float delta, Camera cam){
		rotation = (MathUtils.atan2(vel.y, vel.x) * MathUtils.radiansToDegrees) - 90;
		upperRange = 40 + level;
		lowerRange = 4 + level;
		
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
		
		hunger -= 3 * delta;
		
		if(hunger < 0){
			hunger = 0;
			
			health -= 5 * delta;
		}
	}
	
	public boolean intersects(Particle part){
		return (part.pos.x > pos.x && part.pos.x < pos.x + level && part.pos.y > pos.y && part.pos.y < pos.y + level);
	}
	
	public boolean withinRange(Particle part, float range){
		return (pos.dst(part.pos) < range);
	}
	
	public boolean outsideRange(Particle part, float range){
		return !withinRange(part, range);
	}

	public boolean equalsParticle(Particle part) {
		return (this.id.equals(part.id));
	}
}
