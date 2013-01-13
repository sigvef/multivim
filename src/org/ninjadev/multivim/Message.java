package org.ninjadev.multivim;

import java.io.Serializable;

public class Message implements Serializable{
	public String command;
	public Object payload;
	
	public Message(String command, Object payload){
		this.command = command;
		this.payload = payload;
	}
}
