package org.ninjadev.multivim;

import java.io.Serializable;

public class Position implements Serializable{
	public int row;
	public int column;
	
	public Position(int row, int column){
		this.row = row;
		this.column = column;
	}
	
	public Position(Position p) {
		row = p.row;
		column = p.column;
	}

	public Position(String string) {
		String[] numbers = string.split(":");
		row = Integer.parseInt(numbers[0]);
		column = Integer.parseInt(numbers[1]);
	}

	public String toString(){
		return row+":"+column;
	}
}
