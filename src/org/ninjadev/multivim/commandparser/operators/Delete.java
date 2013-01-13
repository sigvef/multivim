package org.ninjadev.multivim.commandparser.operators;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;

import com.googlecode.lanterna.input.Key;

public class Delete extends AbstractOperator{
	public Delete(){
		commandKey = new Key('d');
	}

	@Override
	public void before() {
	}

	@Override
	public String transform(String character) {
		return character;
	}

	@Override
	public void after(Cursor cursor, Cursor operatorCursor) throws IOException {
		if(cursor.compareTo(operatorCursor) > 0){
		cursor.buffer.delete(cursor, operatorCursor);
		}else{
			cursor.buffer.delete(operatorCursor, cursor);
		}
	}
}
