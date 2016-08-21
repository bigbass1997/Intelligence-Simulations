package com.bigbass1997.intelsim.world.entitygrowth;

import java.util.ArrayList;

public class Attachment {
	
	public int baseID;
	public int motherID;
	public int sideOfMother;
	
	public Attachment(int baseID, int motherID, int sideOfMother){
		this.baseID = baseID;
		this.motherID = motherID;
		this.sideOfMother = sideOfMother;
	}
	
	public void updatePos(ArrayList<Square> squares){
		Square base = squares.get(baseID);
		Square mother = squares.get(motherID);
		
		switch(sideOfMother){
		case Side.TOP:
			base.pos.x = mother.pos.x;
			base.pos.y = mother.pos.y + mother.size;
			break;
		case Side.BOTTOM:
			base.pos.x = mother.pos.x;
			base.pos.y = mother.pos.y - base.size;
			break;
		case Side.LEFT:
			base.pos.x = mother.pos.x - base.size;
			base.pos.y = mother.pos.y;
			break;
		case Side.RIGHT:
			base.pos.x = mother.pos.x + mother.size;
			base.pos.y = mother.pos.y;
			break;
		}
	}
}
