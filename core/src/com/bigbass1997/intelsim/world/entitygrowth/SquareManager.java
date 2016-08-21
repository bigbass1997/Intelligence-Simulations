package com.bigbass1997.intelsim.world.entitygrowth;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SquareManager {
	
	private Random rand;
	
	public ArrayList<Square> squares;
	public ArrayList<Attachment> attaches;
	
	public SquareManager(){
		rand = new Random();
		squares = new ArrayList<Square>();
		attaches = new ArrayList<Attachment>();
	}
	
	public Square addRandomSquare(int color, Camera cam){
		Square square = new Square(rand.nextFloat() * cam.viewportWidth, rand.nextFloat() * cam.viewportHeight, (rand.nextFloat() * 100) - 50, (rand.nextFloat() * 100) - 50, color);
		squares.add(square);
		return square;
	}
	
	public Square addSquare(float x, float y, float dx, float dy, int color){
		Square square = new Square(x, y, dx, dy, color);
		squares.add(square);
		return square;
	}
	
	public void render(ShapeRenderer sr){
		for(int i = squares.size() - 1; i >= 0; i--){
			squares.get(i).render(sr);
		}
	}

	public void update(float delta, Camera cam) {
		for(int i = squares.size() - 1; i >= 0; i--){
			Square base = squares.get(i);
			base.update(delta, cam);
			
			ArrayList<Square> collides = base.checkIntersection(squares);
			for(Square collide : collides){
				attaches.add(new Attachment(collide, base, collide.sideOfMother(base)));
			}
		}
		
		for(int i = attaches.size() - 1; i >= 0; i--){
			attaches.get(i).updatePos(squares);
		}
	}
}
