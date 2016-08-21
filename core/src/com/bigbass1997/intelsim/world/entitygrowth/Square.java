package com.bigbass1997.intelsim.world.entitygrowth;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.bigbass1997.intelsim.skins.SkinManager;

public class Square {
	
	private static int count = -1;
	public String id = String.valueOf((count += 1));
	
	public Vector2 pos;
	public Vector2 vel;
	public float size;
	public Color color;
	
	private Stage stage;
	private Container<Label> idLabelWrapper;
	
	private boolean isIntersecting = false;
	
	public Square[] sideSquares;
	
	public Square(float x, float y, float dx, float dy, int color){
		this.pos = new Vector2(x,y);
		this.vel = new Vector2(dx,dy);
		this.size = 25f;
		this.color = new Color(color);
		
		stage = new Stage();
		
		Label idLabel = new Label(id, SkinManager.getSkin("fonts/computer.ttf", 24));
		idLabel.setColor(Color.RED);
		idLabelWrapper = new Container<Label>(idLabel);
		idLabelWrapper.setTransform(true);
		idLabelWrapper.setOrigin(0, 0);
		idLabelWrapper.setScale(1f);
		
		stage.addActor(idLabelWrapper);
		
		sideSquares = new Square[4];
	}
	
	public void render(ShapeRenderer sr){
		sr.set(ShapeType.Filled);
		sr.setColor(color);
		sr.rect(pos.x - (size / 2), pos.y - (size / 2), size, size);
		
		if(isIntersecting){
			sr.setColor(Color.CORAL);
			sr.rect(pos.x - (size / 2), pos.y - (size / 2), size, size);
		}
		
		sr.end();
		sr.begin();
		stage.draw();
	}
	
	public void update(float delta, Camera cam){
		//Update Position
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
		
		//Update ID Label position
		idLabelWrapper.setPosition(pos.x, pos.y, Align.center);
		stage.act(delta);
	}
	
	public Polygon getPolygonBounds(){
		float half = (size / 2);
		return new Polygon(new float[]{
				pos.x - half, pos.y - half,
				pos.x + half, pos.y - half,
				pos.x + half, pos.y + half,
				pos.x - half, pos.y + half
		});
	}
	
	public int sideOfMother(Square mother){
		
	}
	
	public ArrayList<Square> checkIntersection(ArrayList<Square> otherSquares){
		ArrayList<Square> inter = new ArrayList<Square>();
		//Return Square if intersecting a square
		for(int i = otherSquares.size() - 1; i >= 0; i--){
			Square other = otherSquares.get(i);
			
			if(other.id != this.id){
				
				if(Intersector.overlapConvexPolygons(this.getPolygonBounds(), other.getPolygonBounds())){
					inter.add(other);
				}
				
			}
		}
		
		return inter;
	}
}
