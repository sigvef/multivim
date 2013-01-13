package org.ninjadev.multivim.commandparser.operators;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;

import com.googlecode.lanterna.input.Key;

public class Yank extends AbstractOperator{
	public Yank(){
		commandKey = new Key('y');
	}

	@Override
	public void before() {
	}

	@Override
	public String transform(String character) {
		return null;
	}

	@Override
	public void after(Cursor cursor, Cursor operatorCursor) throws IOException {
	}
}
