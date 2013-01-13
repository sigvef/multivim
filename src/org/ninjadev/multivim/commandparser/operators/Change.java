package org.ninjadev.multivim.commandparser.operators;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;
import org.ninjadev.multivim.Mode;

import com.googlecode.lanterna.input.Key;

public class Change extends AbstractOperator{
	public Change(){
		commandKey = new Key('c');
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
		cursor.user.setMode(Mode.INSERT);
	}
}
