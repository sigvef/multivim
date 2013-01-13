package org.ninjadev.multivim;

import java.io.Serializable;

import com.googlecode.lanterna.terminal.Terminal.Color;

public class UserData implements Serializable{
	public int userId;
	public String name;
	public Color color;
	
	public String toString(){
		return userId+":"+name;
	}
}
