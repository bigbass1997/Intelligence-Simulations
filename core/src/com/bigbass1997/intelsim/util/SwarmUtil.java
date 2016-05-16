package com.bigbass1997.intelsim.util;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.intelsim.world.swarm.Boid;

public class SwarmUtil {
	
	public static void updatePositions(Boid boid, ArrayList<Boid> boids){
		Vector2[] vs = calculateRules(boids, boid);
		
		boid.vel.x += ((vs[0].x * 1.25f) + (vs[1].x * 1.0f) + (vs[2].x * 1.0f));
		boid.vel.y += ((vs[0].y * 1.25f) + (vs[1].y * 1.0f) + (vs[2].y * 1.0f));
		
		boid.vel.nor();
		boid.vel.x *= 300;
		boid.vel.y *= 300;
	}
	
	private static Vector2[] calculateRules(ArrayList<Boid> boids, Boid checkedBoid){
		Vector2[] vs = new Vector2[3];
		int neighbors = 0;
		
		//Initialize array
		for(int i = 0; i < vs.length; i++){
			vs[i] = new Vector2(0, 0);
		}
		
		for(Boid boid : boids){
			if(!boid.equals(checkedBoid)){
				neighbors++;
				
				//rule 1
				if(checkedBoid.pos.dst(boid.pos) < checkedBoid.maxBound){
					vs[0].x += boid.vel.x;
					vs[0].y += boid.vel.y;
				}
				
				//rule 2
				vs[1].x += boid.pos.x;
				vs[1].y += boid.pos.y;
				
				//rule 3
				vs[2].x += boid.pos.x - checkedBoid.pos.x;
				vs[2].y += boid.pos.y - checkedBoid.pos.y;
			}
		}
		
		//rule 1
		if(neighbors != 0){
			vs[0].x /= neighbors; 
			vs[0].y /= neighbors;
			vs[0].nor();
		}
		
		//rule 2
		if(neighbors != 0){
			vs[1].x /= neighbors;
			vs[1].y /= neighbors;
			vs[1].x -= checkedBoid.pos.x;
			vs[1].y -= checkedBoid.pos.y;
			vs[1].nor();
		}
		
		//rule 3
		if(neighbors != 0){
			vs[2].x /= neighbors;
			vs[2].y /= neighbors;
			vs[2].x *= -1;
			vs[2].y *= -1;
			vs[2].nor();
		}
		
		return vs;
	}
	
	public static float safeDivFloat(float numer, float denom){
		float val = 0;
		
		if(denom == 0) return val;
		
		val = numer / denom;
		
		if(Float.isNaN(val)) return 0;
		
		return val;
	}
}
