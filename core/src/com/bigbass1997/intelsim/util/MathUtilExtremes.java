package com.bigbass1997.intelsim.util;

public class MathUtilExtremes {
	
	public static float farthestFromZero(float a, float b){
		if(a == b) return a;
		
		float aa = Math.abs(a);
		float ab = Math.abs(b);
		
		if(aa == ab) return a;
		
		if(aa > ab){
			return a;
		} else {
			return b;
		}
	}
	
	public static float closestToZero(float a, float b){
		if(a == b) return a;
		
		float aa = Math.abs(a);
		float ab = Math.abs(b);
		
		if(aa == ab) return a;
		
		if(aa > ab){
			return b;
		} else {
			return a;
		}
	}
}
