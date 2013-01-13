package org.ninjadev.multivim.commandparser.operators;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;

import com.googlecode.lanterna.input.Key;

public class Insert extends AbstractOperator{
	public Insert() {
		commandKey = new Key('I');
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
	}
}
