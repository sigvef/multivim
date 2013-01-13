package org.ninjadev.multivim.commandparser.operators;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;

import com.googlecode.lanterna.input.Key;

public class Upper extends AbstractOperator{
	public Upper() {
		commandKey = new Key('g');
		secondCommandKey = new Key('U');
	}

	@Override
	public void before() {
	}

	@Override
	public String transform(String character) {
		return character.toUpperCase();
	}

	@Override
	public void after(Cursor cursor, Cursor operatorCursor) throws IOException {
	}
}
