package org.ninjadev.multivim;

import java.io.Serializable;

public class CursorData implements Serializable{
	public Position position;
	
	public CursorData(){
		position = new Position(0,0);
	}
	
	public String toString(){
		return position.toString();
	}
}
