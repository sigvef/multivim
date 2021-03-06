package org.ninjadev.multivim.commandparser.operators;

import java.io.IOException;

import org.ninjadev.multivim.Cursor;

import com.googlecode.lanterna.input.Key;

public class Lower extends AbstractOperator{
	public Lower() {
		commandKey = new Key('g');
		secondCommandKey = new Key('u');
	}

	@Override
	public void before() {
	}

	@Override
	public String transform(String character) {
		return character.toLowerCase();
	}

	@Override
	public void after(Cursor cursor, Cursor operatorCursor) throws IOException {
	}
}
