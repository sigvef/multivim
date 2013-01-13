package org.ninjadev.multivim.commandparser.operators;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;

public class Movement extends AbstractOperator{
	public Movement() {
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
		cursor.set(operatorCursor);
	}
}
